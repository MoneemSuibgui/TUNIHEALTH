package tunihealth.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import tunihealth.models.LoggedUser;
import tunihealth.models.Patient;
import tunihealth.repositories.PatientRepository;

@Service
public class PatientService {

	// inject PatientRepository
	@Autowired
	private PatientRepository repository;


	// Register  method
	public void register(Patient patient, BindingResult result) {

		Optional<Patient> optionalPatient = this.repository.findByEmail(patient.getEmail());
		// check if email exist in db
		if (optionalPatient.isPresent()) {
			result.rejectValue("email", "emailMatch", "Email is already token");
		}
		// check if password and confirm match or no
		if (!(patient.getPassword().equals(patient.getConfirm()))) {
			result.rejectValue("confirm", "PwMatch", "Password & Confirm doesn't matches");
		}
	}
	

	// Login  method
	public Patient login(LoggedUser loggedUser, BindingResult result) {
		Optional<Patient> optionalPatient = this.repository.findByEmail(loggedUser.getEmail());
		// check if email of logged patient in the database & the password match or not
		if (optionalPatient.isPresent() && BCrypt.checkpw(loggedUser.getPassword(),optionalPatient.get().getPassword())) {
			return optionalPatient.get();
		}
		result.rejectValue("email", "INCredential", "Invalid Credentials");
		return null;
	}

	// method for hashing password of the patient
	public void hashPassword(Patient patient) {
		patient.setPassword(BCrypt.hashpw(patient.getPassword(), BCrypt.gensalt()));
	}

	// save patient
	@Transactional
	public Patient savePatient(@NonNull Patient patient) {
		return this.repository.save(patient);
	}

	// find by id
	public Patient GetById(@NonNull Long id) {
		Optional<Patient> optionalPatient = this.repository.findById(id);
		if (optionalPatient.isPresent()) {
			return optionalPatient.get();
		}
		return null;
	}

	// get all
	public List<Patient> allPatients() {
		return this.repository.findAll();
	}
	
	// update patient
	public Patient update(Patient patient) {
		return this.repository.save(patient);
	}

}
