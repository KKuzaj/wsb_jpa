package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PatientDao extends Dao<PatientEntity, Long> {
    PatientEntity findByPatientNumber(String patientNumber);

    void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime time, String description);

    List<PatientEntity> findByName(String name);

    List<PatientEntity> findPatientsWithMoreThanXVisits(long visitCount);

    List<PatientEntity> findPatientsBornAfter(LocalDate date);

}