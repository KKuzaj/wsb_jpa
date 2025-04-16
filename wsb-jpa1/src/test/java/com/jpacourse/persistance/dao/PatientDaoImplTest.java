package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PatientDaoImplTest {

    @Autowired
    private PatientDao patientDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testAddVisitToPatient_shouldAddVisitWithDoctor() {

        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("Anna");
        doctor.setLastName("Wójcik");
        entityManager.persist(doctor);

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Tomasz");
        patient.setLastName("Biel");
        patient.setPatientNumber("P200");
        patient.setDateOfBirth(LocalDate.of(1988, 12, 5));
        patient.setTelephoneNumber("789456123");
        patient.setEmail("t.biel@example.com");
        patient.setInsured(true);
        entityManager.persist(patient);
        entityManager.flush();

        LocalDateTime time = LocalDateTime.now().minusDays(1);
        String description = "Wizyta kontrolna";

        patientDao.addVisitToPatient(patient.getId(), doctor.getId(), time, description);

        entityManager.flush();
        entityManager.clear();

        PatientEntity updated = entityManager.find(PatientEntity.class, patient.getId());
        assertThat(updated.getVisitEntityList()).hasSize(1);

        VisitEntity visit = updated.getVisitEntityList().get(0);
        assertThat(visit.getTime()).isEqualTo(time);
        assertThat(visit.getDescription()).isEqualTo(description);
        assertThat(visit.getDoctorEntity()).isNotNull();
        assertThat(visit.getDoctorEntity().getFirstName()).isEqualTo("Anna");
    }
}