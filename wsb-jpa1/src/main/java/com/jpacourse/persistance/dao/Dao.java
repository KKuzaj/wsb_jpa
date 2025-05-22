package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public interface Dao<T, K extends Serializable> {

    T save(T entity);

    T getOne(K id);

    T findOne(K id);

    List<T> findAll();

    T update(T entity);

    void delete(T entity);

    void delete(K id);

    void deleteAll();

    long count();

    boolean exists(K id);

    List<PatientEntity> findPatientsBornAfter(LocalDate date);
}
