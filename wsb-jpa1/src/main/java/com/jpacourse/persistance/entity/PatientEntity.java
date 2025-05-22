package com.jpacourse.persistance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "PATIENT")
public class PatientEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String telephoneNumber;

    private String email;

    @Column(nullable = false)
    private String patientNumber;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column
    private Boolean insured;

    @Version
    private Integer version;

    @OneToMany(mappedBy = "patientEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)

    @Fetch(FetchMode.SELECT)
    private List<VisitEntity> visitEntityList;

    @OneToMany(mappedBy = "patientEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AddressEntity> addressEntities;


    public Long getId() {
        return id;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean isInsured() {
        return insured;
    }

    public void setInsured(boolean insured) {
        this.insured = insured;
    }

    public List<AddressEntity> getAddressEntities() {
        return addressEntities;
    }

    public void setAddressEntities(List<AddressEntity> addressEntities) {
        this.addressEntities = addressEntities;
    }

    public List<VisitEntity> getVisitEntityList() {
        return visitEntityList;
    }

    public void setVisitEntityList(List<VisitEntity> visitEntityList) {
        this.visitEntityList = visitEntityList;
    }

    public void setId(Long id) {
        this.id = id;
    }
}