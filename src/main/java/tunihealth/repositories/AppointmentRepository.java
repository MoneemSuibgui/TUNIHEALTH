package tunihealth.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tunihealth.models.Appointment;
import tunihealth.models.Doctor;
import tunihealth.models.Patient;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
	
	// find all appointments belongs to patient
	List<Appointment> findByPatient(Patient patient);
	
	// find all appointments belongs to doctor
	List<Appointment> findByDoctor(Doctor doctor);
	
	// find all
	List<Appointment> findAll();
	
	
}
