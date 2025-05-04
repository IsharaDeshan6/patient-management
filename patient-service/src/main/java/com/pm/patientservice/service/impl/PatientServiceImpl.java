package com.pm.patientservice.service.impl;

import com.pm.patientservice.dto.request.PatientRequestDTO;
import com.pm.patientservice.dto.response.PagedPatientResponseDTO;
import com.pm.patientservice.dto.response.PatientResponseDTO;
import com.pm.patientservice.entity.Patient;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.kafka.KafkaProducer;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.repository.PatientRepository;
import com.pm.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    @Override
    @Cacheable(
            value = "patients",
            key = "#page + '-' + #size + '-' + #sortBy + '-' + #sortField + '-' + #searchValue",
            condition = "#searchValue == ''"
    )
    public PagedPatientResponseDTO getAllPatients(int page, int size, String sortBy, String sortField, String searchValue) {

        log.info("[REDIS] : Cache miss - fetching data from database");

        try {
            Thread.sleep(2000);
        }catch (InterruptedException e){
            log.error(e.getMessage());
        }

        //request -> page = 1
        //pageable -> page = 0
        Pageable pageable = PageRequest.of(page -1, size,
                sortBy.equalsIgnoreCase("desc")
                        ? Sort.by(sortField).descending()
                        : Sort.by(sortField).ascending());

        Page<Patient> patientPage;

        if (searchValue == null || searchValue.isBlank()) {
            patientPage = patientRepository.findAll(pageable);
        } else {
            patientPage = patientRepository.findByNameContainingIgnoreCase(searchValue, pageable);
        }

        List<PatientResponseDTO> patientResponseDTOS = patientPage.getContent().stream()
                .map(PatientMapper::toDTO).toList();

        return new PagedPatientResponseDTO(
                patientResponseDTOS,
                patientPage.getNumber() + 1,
                patientPage.getSize(),
                patientPage.getTotalPages(),
                (int) patientPage.getTotalElements()
        );

    }

    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {

        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email already exists" + patientRequestDTO.getEmail());
        }

        Patient newPatient = patientRepository.save(PatientMapper.toEntity(patientRequestDTO));
        billingServiceGrpcClient.createBillingAccount(
                newPatient.getId().toString(),
                newPatient.getName(),
                newPatient.getEmail());
        kafkaProducer.sendEvent(newPatient);


        return PatientMapper.toDTO(newPatient);
    }

    @Override
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with id: " + id));

        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("A patient with this email already exists" + patientRequestDTO.getEmail());
        }

        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);

    }

    @Override
    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }
}
