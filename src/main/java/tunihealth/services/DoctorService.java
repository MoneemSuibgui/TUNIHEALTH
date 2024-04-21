package tunihealth.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import tunihealth.models.Doctor;
import tunihealth.models.LoggedUser;
import tunihealth.repositories.DoctorRepository;

@Service
public class DoctorService {

	// inject DoctorRepository
	private DoctorRepository repository;

	public DoctorService(DoctorRepository repository) {
		this.repository = repository;
	}

	// Register method
	public void register(Doctor doctor, BindingResult result) {
		Optional<Doctor> optionalDoctor = this.repository.findByEmail(doctor.getEmail());
		if (optionalDoctor.isPresent()) {
			result.rejectValue("email", "EmailMatch", "* Email already token");
		}
		if (!doctor.getPassword().equals(doctor.getConfirm())) {
			result.rejectValue("confirm", "PwMatch", "* Paswword & confirm PW not matches");
		}
	}

	// Login method
	public Doctor login(LoggedUser loggedDoctor, BindingResult result) {
		Optional<Doctor> optionalDoctor = this.repository.findByEmail(loggedDoctor.getEmail());
		if (optionalDoctor.isPresent()
				&& BCrypt.checkpw(loggedDoctor.getPassword(), optionalDoctor.get().getPassword())) {
			return optionalDoctor.get();
		}
		result.rejectValue("email", "IVdata", "* Invalid Credentials !!!");
		return null;
	}

	// hash doctor passowrd
	public void hashPasswordDoctor(Doctor doctor) {
		doctor.setPassword(BCrypt.hashpw(doctor.getPassword(), BCrypt.gensalt()));
	}

	// Save Doctor
	public Doctor add(Doctor doctor) {
		return this.repository.save(doctor);
	}

	// update Doctor
	public Doctor update(Doctor doctor) {
		return this.repository.save(doctor);
	}


	// Get doctor by id
	public Doctor GetById(Long id) {
		Optional<Doctor> optionalDoctor = this.repository.findById(id);
		if (optionalDoctor.isPresent()) {
			return optionalDoctor.get();
		}
		return null;
	}

	// Get doctor by fullName
	public Doctor GetByFullName(String name) {
		Optional<Doctor> optionalDoctor = this.repository.findByFullNameContains(name);
		if (optionalDoctor.isPresent()) {
			return optionalDoctor.get();
		}
		return null;
	}
	

	// get all doctors
	public List<Doctor> getAll() {
		return this.repository.findAll();
	}

}
