package tunihealth.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import tunihealth.abstracts.DBEntity;

@Entity
@Table(name = "appointments")
public class Appointment extends DBEntity{
	// Inherit id ,createdAt,updatedAt from DBEntity

	// member variables
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "Appointment date must be in future")
	private LocalDate date;

	private LocalTime time;

	@NotEmpty(message = "Reason must not be empty")
	@Size(min = 10, message = "Reason must be at least 10 characters")
	private String reason;

	// Many appointments can belongs to one patient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	private Patient patient;

	// n:1 connection between doctor and appointments
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;

	// n:n relationship between doctors and appointments(middel table for validated
	// appointments of doctors)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "validated_appointments", joinColumns = @JoinColumn(name = "appointment_id"), inverseJoinColumns = @JoinColumn(name = "doctor_id"))
	private List<Doctor> doctors;

	// beans constructor
	public Appointment() {
	}

	// Full args Constructor
	public Appointment(LocalDate date, LocalTime time, String reason, Patient patient, Doctor doctor) {
		this.date = date;
		this.time = time;
		this.reason = reason;
		this.patient = patient;
		this.doctor = doctor;
	}

	// getters & setters
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

}
