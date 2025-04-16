package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.entity.MedicalTreatmentEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;

import java.util.List;
import java.util.stream.Collectors;

public final class PatientMapper {

    private PatientMapper() {
    }

    public static PatientTO mapToTO(final PatientEntity entity) {
        if (entity == null) {
            return null;
        }

        final PatientTO to = new PatientTO();
        to.setId(entity.getId());
        to.setFirstName(entity.getFirstName());
        to.setLastName(entity.getLastName());
        to.setTelephoneNumber(entity.getTelephoneNumber());
        to.setEmail(entity.getEmail());
        to.setPatientNumber(entity.getPatientNumber());
        to.setDateOfBirth(entity.getDateOfBirth());
        to.setInsured(entity.isInsured());

        if (entity.getVisitEntityList() != null) {
            List<VisitTO> visitTOS = entity.getVisitEntityList().stream()
                    .filter(v -> v.getTime().isBefore(java.time.LocalDateTime.now()))
                    .map(PatientMapper::mapVisitToTO)
                    .collect(Collectors.toList());
            to.setVisits(visitTOS);
        }

        return to;
    }

    public static PatientEntity mapToEntity(final PatientTO to) {
        if (to == null) {
            return null;
        }

        final PatientEntity entity = new PatientEntity();
        entity.setId(to.getId());
        entity.setFirstName(to.getFirstName());
        entity.setLastName(to.getLastName());
        entity.setTelephoneNumber(to.getTelephoneNumber());
        entity.setEmail(to.getEmail());
        entity.setPatientNumber(to.getPatientNumber());
        entity.setDateOfBirth(to.getDateOfBirth());
        entity.setInsured(to.isInsured());


        return entity;
    }

    private static VisitTO mapVisitToTO(final VisitEntity visit) {
        if (visit == null) {
            return null;
        }

        final VisitTO to = new VisitTO();
        to.setTime(visit.getTime());

        if (visit.getDoctorEntity() != null) {
            to.setDoctorFirstName(visit.getDoctorEntity().getFirstName());
            to.setDoctorLastName(visit.getDoctorEntity().getLastName());
        }

        if (visit.getMedicalTreatmentEntity() != null) {
            List<String> treatmentTypes = visit.getMedicalTreatmentEntity().stream()
                    .map(x -> x.getType().name())
                    .collect(Collectors.toList());
            to.setTreatmentTypes(treatmentTypes);
        }

        return to;
    }
}