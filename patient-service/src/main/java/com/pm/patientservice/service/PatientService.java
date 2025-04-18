package com.pm.patientservice.service;

import com.pm.patientservice.dto.response.PatientResponseDTO;

import java.util.List;

public interface PatientService {

    List<PatientResponseDTO> getAllPatients();
}
