package com.jpacourse.persistance.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "VISIT")
public class VisitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	private LocalDateTime time;

	@Version
	private Integer version;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private DoctorEntity doctorEntity;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<MedicalTreatmentEntity> medicalTreatmentEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	private PatientEntity patientEntity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public DoctorEntity getDoctorEntity() {
		return doctorEntity;
	}

	public void setDoctorEntity(DoctorEntity doctorEntity) {
		this.doctorEntity = doctorEntity;
	}

	public List<MedicalTreatmentEntity> getMedicalTreatmentEntity() {
		return medicalTreatmentEntity;
	}

	public void setMedicalTreatmentEntity(List<MedicalTreatmentEntity> medicalTreatmentEntity) {
		this.medicalTreatmentEntity = medicalTreatmentEntity;
	}

	public PatientEntity getPatientEntity() {
		return patientEntity;
	}

	public void setPatientEntity(PatientEntity patientEntity) {
		this.patientEntity = patientEntity;
	}

	public void setVisitDate(LocalDate now) {
	}
}