package tunihealth.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tunihealth.models.Appointment;
import tunihealth.models.Doctor;
import tunihealth.models.Patient;
import tunihealth.repositories.AppointmentRepository;

@Service
public class AppointmentService {

	// inject AppointmentRepository
	@Autowired
	private AppointmentRepository repository;

	// create appointment
	public Appointment add(LocalDate date, LocalTime time, String reason, Patient patient, Doctor doctor) {
		Appointment newAppointment = new Appointment(date, time, reason, patient, doctor);
		return this.repository.save(newAppointment);
	}


	// update appointment
	public Appointment update(Appointment appointment,Patient patient) {
		appointment.setPatient(patient);
		return this.repository.save(appointment);
	}

	// find all appointments belongs to patient
	public List<Appointment> getAllAppointmentByPatient(Patient patient) {
		return this.repository.findByPatient(patient);
	}

	// find all appointments belongs to doctor
	public List<Appointment> getAllAppointmentByDoctor(Doctor doctor) {
		return this.repository.findByDoctor(doctor);
	}
	
	// find all appointments 
		public List<Appointment> getAll() {
			return this.repository.findAll();
		}

	// find by id
	public Appointment GetById(Long id) {
		Optional<Appointment> optionalAppointment = this.repository.findById(id);
		if (optionalAppointment.isPresent()) {
			return optionalAppointment.get();
		}
		return null;
	}
	
	// delete appointment by id
	public void deleteAppointment(Patient patient,Long appointmentId) {
		Appointment appointment=this.GetById(appointmentId);
		appointment.setPatient(null);
		this.repository.deleteById(appointmentId);
	}

}
