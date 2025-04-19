package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.request.PatientRequestDTO;
import com.pm.patientservice.dto.response.PatientResponseDTO;
import com.pm.patientservice.entity.Patient;

import java.time.LocalDate;
import java.util.UUID;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient) {
        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setId(patient.getId().toString());
        patientResponseDTO.setName(patient.getName());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setDateOfBirth(patient.getDateOfBirth().toString());

        return patientResponseDTO;

    }
    public static Patient toEntity(PatientRequestDTO patePatientRequestDTO) {
        Patient patient = new Patient();
        patient.setName(patePatientRequestDTO.getName());
        patient.setEmail(patePatientRequestDTO.getEmail());
        patient.setAddress(patePatientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patePatientRequestDTO.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.now());
        return patient;
    }
}
