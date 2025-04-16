package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;

import java.time.LocalDateTime;

public interface PatientDao extends Dao<PatientEntity, Long> {
    PatientEntity findByPatientNumber(String patientNumber);
    void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime time, String description);
}