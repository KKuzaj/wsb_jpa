package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistance.dao.AddressDao;
import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class AddressDaoImpl extends AbstractDao<AddressEntity, Long> implements AddressDao
{

    @Override
    public List<PatientEntity> findPatientsBornAfter(LocalDate date) {
        return List.of();
    }
}
