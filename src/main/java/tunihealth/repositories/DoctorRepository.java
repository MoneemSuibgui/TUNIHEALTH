package tunihealth.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tunihealth.models.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {

	Optional<Doctor> findByEmail(String email);
	Optional<Doctor> findByFullNameContains(String name);
	List<Doctor> findAll();
	
	
}
