package dev.alexgiou.patientservice.service;

import dev.alexgiou.patientservice.dto.PatientRequest;
import dev.alexgiou.patientservice.dto.PatientResponse;
import dev.alexgiou.patientservice.exception.EmailAlreadyExistsException;
import dev.alexgiou.patientservice.exception.PatientNotFoundException;
import dev.alexgiou.patientservice.grpc.BillingServiceGrpcClient;
import dev.alexgiou.patientservice.kafka.KafkaProducer;
import dev.alexgiou.patientservice.mapper.PatientMapper;
import dev.alexgiou.patientservice.model.Patient;
import dev.alexgiou.patientservice.repository.PatientRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PatientService {

  private final PatientRepository patientRepository;
  private final BillingServiceGrpcClient billingServiceGrpcClient;
  private final KafkaProducer kafkaProducer;

  public List<PatientResponse> getAllPatients() {
    return patientRepository.findAll()
        .stream()
        .map(PatientMapper::toDto)
        .toList();
  }

  @Transactional
  public PatientResponse createPatient(PatientRequest patientRequest) {
    if (patientRepository.existsByEmail(patientRequest.getEmail())) {
      throw new EmailAlreadyExistsException("A patient with this email already exists.");
    }

    Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequest));

    billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(), newPatient.getName(), newPatient.getEmail());

    kafkaProducer.sendEvent(newPatient);

    return PatientMapper.toDto(newPatient);
  }

  public PatientResponse updatePatient(UUID id, PatientRequest patientRequest) {
    Patient patient = patientRepository.findById(id)
        .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));

    if (patientRepository.existsByEmailAndIdNot(patientRequest.getEmail(), id)) {
      throw new EmailAlreadyExistsException("A patient with this email already exists.");
    }

    patient.setName(patientRequest.getName());
    patient.setEmail(patientRequest.getEmail());
    patient.setAddress(patientRequest.getAddress());
    patient.setDateOfBirth(LocalDate.parse(patientRequest.getDateOfBirth()));

    Patient updatedPatient = patientRepository.save(patient);
    return PatientMapper.toDto(updatedPatient);
  }


  public void deletePatient(UUID id) {
    if (!patientRepository.existsById(id)) {
      throw new PatientNotFoundException("Patient not found with id: " + id);
    }
    patientRepository.deleteById(id);
  }


}
