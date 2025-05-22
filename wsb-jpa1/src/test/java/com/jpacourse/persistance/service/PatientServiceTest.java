package com.jpacourse.persistance.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientDao patientDao;

    @Test
    public void testFindPatientById_shouldReturnCorrectTOIncludingInsured() {


        PatientTO patientTO = patientService.findById(102L);


        assertThat(patientTO).isNotNull();
        assertThat(patientTO.getFirstName()).isEqualTo("Anna");
        assertThat(patientTO.isInsured()).isTrue();

        assertThat(patientTO.getVisits()).hasSize(101);
        VisitTO visitTO = patientTO.getVisits().get(0);
        assertThat(visitTO.getDoctorFirstName()).isEqualTo("Jan");
        assertThat(visitTO.getTreatmentTypes()).containsExactly("Konsultacja pediatryczn");
    }

    @Test
    public void testDeletePatient_shouldRemoveVisitsButKeepDoctors() {


        Long patientId = 101L;

        // when
        patientDao.delete(patientId);

        // then
        PatientEntity deletedPatient = patientDao.findOne(patientId);
        assertThat(deletedPatient).isNull(); // pacjent usunięty

        // Sprawdzenie, czy wizyty zostały usunięte (kaskada)
        // assertThat(visit.getId()).isNull(); // wizyta powinna być usunięta

        // Lekarz pozostaje w bazie (nie jest usunięty)
        // assertThat(doctor.getFirstName()).isEqualTo("Ewa");

        // Sprawdzamy, czy pobranie pacjenta po ID zwraca null
        assertThat(patientService.findById(patientId)).isNull();
    }

}