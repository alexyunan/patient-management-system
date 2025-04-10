package dev.alexgiou.patientservice.repository;

import dev.alexgiou.patientservice.model.Patient;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, UUID>{

 boolean existsByEmail (String email);

 boolean existsByEmailAndIdNot (String email, UUID id);

}
