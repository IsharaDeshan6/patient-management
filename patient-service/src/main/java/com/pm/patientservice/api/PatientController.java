package com.pm.patientservice.api;

import com.pm.patientservice.dto.response.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;
import com.pm.patientservice.util.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


}
