package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;

import java.util.List;

public interface PatientService {
    PatientTO findById(Long id);
    PatientTO findByPatientNumber(String patientNumber);

    List<VisitTO>findVisitByPatientId(Long id);

}