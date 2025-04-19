package com.pm.patientservice.service;

import com.pm.patientservice.dto.request.PatientRequestDTO;
import com.pm.patientservice.dto.response.PatientResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PatientService {

    List<PatientResponseDTO> getAllPatients();
    PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO);
    PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO);
}
