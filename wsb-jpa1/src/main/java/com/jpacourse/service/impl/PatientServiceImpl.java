package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientDao patientDao;

    @Autowired
    public PatientServiceImpl(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    @Override
    public PatientTO findById(Long id) {
        PatientEntity entity = patientDao.findOne(id);
        return PatientMapper.mapToTO(entity);
    }

    @Override
    public PatientTO findByPatientNumber(String patientNumber) {
        PatientEntity entity = patientDao.findByPatientNumber(patientNumber);
        return PatientMapper.mapToTO(entity);
    }

    @Override
    public List<VisitTO> findVisitByPatientId(Long id) {
        return findById(id).getVisits();
    }
}