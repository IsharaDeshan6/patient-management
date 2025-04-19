package com.pm.patientservice.api;

import com.pm.patientservice.dto.request.PatientRequestDTO;
import com.pm.patientservice.dto.response.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;
import com.pm.patientservice.util.StandardResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("patient-service/api/v1/patients")
@RestController
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;


    @GetMapping
    public ResponseEntity<StandardResponse> getPatients(){
        List<PatientResponseDTO> allPatients = patientService.getAllPatients();
        return new ResponseEntity<>(
                new StandardResponse(200,"Patients retrieved successfully",allPatients),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<StandardResponse> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patient = patientService.createPatient(patientRequestDTO);
        return new ResponseEntity<>(
                new StandardResponse(201,"Patient created successfully",patient),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandardResponse> updatePatient(@PathVariable UUID id, @Valid @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO updatedPatient = patientService.updatePatient(id, patientRequestDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Patient updated successfully",updatedPatient),
                HttpStatus.OK
        );
    }


}
