package com.pm.patientservice.controller;

import com.pm.patientservice.dto.request.PatientRequestDTO;
import com.pm.patientservice.dto.response.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;
import com.pm.patientservice.util.StandardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("patient-service/v1/patients")
@RestController
@RequiredArgsConstructor
@Tag(name = "Patient", description = "API for managing patients")
public class PatientController {

    private final PatientService patientService;


    @GetMapping
    @Operation(summary = "Get Patients")
    public ResponseEntity<StandardResponse> getPatients(){
        List<PatientResponseDTO> allPatients = patientService.getAllPatients();
        return new ResponseEntity<>(
                new StandardResponse(200,"Patients retrieved successfully",allPatients),
                HttpStatus.OK
        );
    }

    @PostMapping
    @Operation(summary = "Create a new Patient")
    public ResponseEntity<StandardResponse> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patient = patientService.createPatient(patientRequestDTO);
        return new ResponseEntity<>(
                new StandardResponse(201,"Patient created successfully",patient),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Patient")
    public ResponseEntity<StandardResponse> updatePatient(@PathVariable UUID id, @Valid @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO updatedPatient = patientService.updatePatient(id, patientRequestDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Patient updated successfully",updatedPatient),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Patien t")
    public ResponseEntity<StandardResponse> deletePatient(@PathVariable UUID id){
        patientService.deletePatient(id);
        return new ResponseEntity<>(
                new StandardResponse(200,"Patient deleted successfully",null),
                HttpStatus.OK
        );
    }


}
