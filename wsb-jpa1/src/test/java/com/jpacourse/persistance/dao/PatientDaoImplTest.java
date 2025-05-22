package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    public void shouldFindPatientByName() {
        String name = "Kowalski";
        List<PatientEntity> patientEntities = patientDao.findByName(name);
        assertThat(patientEntities).hasSize(1);
        assertThat(patientEntities.get(0).getLastName()).isEqualTo(name);
    }

    @Test
    public void shouldFindPatientsWithMoreThanXVisits() {
        PatientEntity patient1 = new PatientEntity();
        patient1.setFirstName("Jan");
        patient1.setLastName("Kowalski");
        patient1.setPatientNumber("P001");
        patient1.setDateOfBirth(LocalDate.of(1980, 1, 1));
        patient1.setInsured(true);
        entityManager.persist(patient1);

        PatientEntity patient2 = new PatientEntity();
        patient2.setFirstName("Anna");
        patient2.setLastName("Nowak");
        patient2.setPatientNumber("P002");
        patient2.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patient2.setInsured(true);
        entityManager.persist(patient2);

        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("Ewa");
        doctor.setLastName("Lekarz");
        entityManager.persist(doctor);

        for (int i = 0; i < 3; i++) {
            patientDao.addVisitToPatient(patient1.getId(), doctor.getId(), LocalDateTime.now().minusDays(i), "Wizyta " + i);
        }

        entityManager.flush();
        entityManager.clear();

        List<PatientEntity> result = patientDao.findPatientsWithMoreThanXVisits(2);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPatientNumber()).isEqualTo("P001");
    }

    @Test
    public void shouldFindPatientsBornAfterDate() {
        PatientEntity oldPatient = new PatientEntity();
        oldPatient.setFirstName("Stary");
        oldPatient.setLastName("Pacjent");
        oldPatient.setPatientNumber("OLD001");
        oldPatient.setDateOfBirth(LocalDate.of(1970, 1, 1));
        oldPatient.setInsured(true);
        entityManager.persist(oldPatient);

        PatientEntity newPatient = new PatientEntity();
        newPatient.setFirstName("Młody");
        newPatient.setLastName("Pacjent");
        newPatient.setPatientNumber("NEW001");
        newPatient.setDateOfBirth(LocalDate.of(2000, 1, 1));
        newPatient.setInsured(true);
        entityManager.persist(newPatient);

        entityManager.flush();
        entityManager.clear();

        List<PatientEntity> result = patientDao.findPatientsBornAfter(LocalDate.of(1990, 1, 1));
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPatientNumber()).isEqualTo("NEW001");
    }

    @Test
    public void testFetchPatientWithVisits_SelectFetch() {
        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Adam");
        patient.setLastName("Nowak");
        patient.setPatientNumber("P002");
        patient.setTelephoneNumber("000");
        patient.setDateOfBirth(LocalDate.of(1999, 9, 9));
        patient.setInsured(true);

        VisitEntity visit1 = new VisitEntity();
        visit1.setPatientEntity(patient);
        visit1.setTime(LocalDateTime.now());

        VisitEntity visit2 = new VisitEntity();
        visit2.setPatientEntity(patient);
        visit2.setTime(LocalDateTime.now().plusDays(1));

        patient.setVisitEntityList(List.of(visit1, visit2));

        entityManager.persist(patient);
        entityManager.flush();
        entityManager.clear();

        PatientEntity fetched = entityManager.find(PatientEntity.class, patient.getId());
        System.out.println("Visits (SELECT): " + fetched.getVisitEntityList().size());
        assertThat(fetched.getVisitEntityList()).hasSize(2);
    }

    @Test
    public void shouldThrowOptimisticLockExceptionOnConcurrentVisitUpdate() {
        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Piotr");
        patient.setLastName("Testowy");
        patient.setPatientNumber("P777");
        patient.setDateOfBirth(LocalDate.of(1991, 3, 15));
        patient.setTelephoneNumber("123456789");
        patient.setEmail("piotr@example.com");
        patient.setInsured(true);

        VisitEntity visit = new VisitEntity();
        visit.setDescription("Wizyta równoległa");
        visit.setTime(LocalDateTime.now());
        visit.setPatientEntity(patient);

        patient.setVisitEntityList(List.of(visit));

        entityManager.persist(patient);
        entityManager.flush();
        entityManager.clear();

        EntityManagerFactory emf = entityManager.getEntityManagerFactory();
        EntityManager em1 = emf.createEntityManager();
        EntityManager em2 = emf.createEntityManager();

        VisitEntity visit1 = em1.find(VisitEntity.class, visit.getId());
        VisitEntity visit2 = em2.find(VisitEntity.class, visit.getId());

        em1.getTransaction().begin();
        visit1.setDescription("Zmienione przez sesję 1");
        em1.getTransaction().commit();
        em1.close();

        assertThrows(OptimisticLockException.class, () -> {
            em2.getTransaction().begin();
            visit2.setDescription("Zmienione przez sesję 2");
            em2.getTransaction().commit(); // powinien rzucić wyjątek
            em2.close();
        });
    }
}
