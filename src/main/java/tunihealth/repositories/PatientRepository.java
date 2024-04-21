package tunihealth.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tunihealth.models.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
	List<Patient> findAll();
	Optional<Patient> findByEmail(String Email);

}
