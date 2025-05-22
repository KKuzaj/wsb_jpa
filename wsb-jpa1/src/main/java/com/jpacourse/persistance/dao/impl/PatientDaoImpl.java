package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<PatientEntity> findByName(String name) {
        return entityManager.createQuery("SELECT patient FROM PatientEntity patient " +
                        "WHERE patient.lastName LIKE :param1", PatientEntity.class)
                .setParameter("param1", "%" + name + "%")
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsWithMoreThanXVisits(long visitCount) {
        return entityManager.createQuery(
                        "SELECT p FROM PatientEntity p WHERE SIZE(p.visitEntityList) > :count", PatientEntity.class)
                .setParameter("count", (int) visitCount)
                .getResultList();
    }
    @Override
    public List<PatientEntity> findPatientsBornAfter(LocalDate date) {
        return entityManager.createQuery(
                        "SELECT p FROM PatientEntity p WHERE p.dateOfBirth > :date", PatientEntity.class)
                .setParameter("date", date)
                .getResultList();
    }
}
