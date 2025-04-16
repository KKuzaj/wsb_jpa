package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {

    @Override
    public PatientEntity findByPatientNumber(String patientNumber) {
        try {
            return entityManager
                    .createQuery("SELECT p FROM PatientEntity p WHERE p.patientNumber = :number", PatientEntity.class)
                    .setParameter("number", patientNumber)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime time, String description) {
        PatientEntity patient = entityManager.find(PatientEntity.class, patientId);
        DoctorEntity doctor = entityManager.find(DoctorEntity.class, doctorId);

        if (patient == null || doctor == null) {
            throw new IllegalArgumentException("Patient or doctor not found");
        }

        VisitEntity visit = new VisitEntity();
        visit.setTime(time);
        visit.setDescription(description);
        visit.setDoctorEntity(doctor);
        visit.setPatientEntity(patient); // ustawienie relacji dwukierunkowej

        if (patient.getVisitEntityList() == null) {
            patient.setVisitEntityList(new ArrayList<>());
        }
        patient.getVisitEntityList().add(visit);

        entityManager.merge(patient); // zapis kaskadowy
    }
}