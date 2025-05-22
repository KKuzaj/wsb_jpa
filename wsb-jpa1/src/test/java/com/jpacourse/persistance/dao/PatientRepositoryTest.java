package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PatientDaoImplTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private PatientDao patientDao;

    @BeforeAll
    public static void setupEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory("yourPersistenceUnitName");
    }

    @AfterAll
    public static void closeEntityManagerFactory() {
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    public void setupEntityManager() {
        em = emf.createEntityManager();
        patientDao = new PatientDaoImpl();
        ((PatientDaoImpl) patientDao).setEntityManager(em);
    }

    @AfterEach
    public void closeEntityManager() {
        if (em != null) {
            em.close();
        }
    }

    @Test
    public void shouldFindPatientByName() {
        // given
        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Jan");
        patient.setLastName("Kowalski");
        patient.setPatientNumber("P001");

        em.getTransaction().begin();
        em.persist(patient);
        em.getTransaction().commit();

        // when
        List<PatientEntity> found = patientDao.findByName("Kowalski");

        // then
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getLastName()).isEqualTo("Kowalski");
    }

    @Test
    public void testFetchPatientWithVisits_SelectFetch() {
        em.getTransaction().begin();

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Adam");
        patient.setLastName("Nowak");
        patient.setPatientNumber("P002");

        VisitEntity visit1 = new VisitEntity();
        visit1.setPatientEntity(patient);
        visit1.setVisitDate(LocalDate.now());

        VisitEntity visit2 = new VisitEntity();
        visit1.setTime(LocalDateTime.now());
        visit2.setTime(LocalDateTime.now().plusDays(1));

        patient.setVisitEntityList(List.of(visit1, visit2));

        em.persist(patient);

        em.getTransaction().commit();

        em.clear(); // Wyczyść cache, wymuś ponowne pobranie

        em.getTransaction().begin();
        PatientEntity fetchedPatient = em.find(PatientEntity.class, patient.getId());

        System.out.println("Visits count: " + fetchedPatient.getVisitEntityList().size());

        em.getTransaction().commit();
    }
}
