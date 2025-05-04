package com.pm.patientservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PagedPatientResponseDTO {
    private List<PatientResponseDTO> patients;
    private int page;
    private int size;
    private int totalPages;
    private int totalElements;
}
