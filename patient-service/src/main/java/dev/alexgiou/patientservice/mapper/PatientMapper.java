package dev.alexgiou.patientservice.mapper;

import dev.alexgiou.patientservice.dto.PatientRequest;
import dev.alexgiou.patientservice.dto.PatientResponse;
import dev.alexgiou.patientservice.model.Patient;
import java.time.LocalDate;

public class PatientMapper {

  public static PatientResponse toDto(Patient patient) {
    PatientResponse patientResponse = new PatientResponse();
    patientResponse.setId(patient.getId().toString());
    patientResponse.setName(patient.getName());
    patientResponse.setEmail(patient.getEmail());
    patientResponse.setAddress(patient.getAddress());
    patientResponse.setDateOfBirth(patient.getDateOfBirth().toString());
    return patientResponse;
  }

  public static Patient toModel(PatientRequest patientRequest) {
    Patient patient = new Patient();
    patient.setName(patientRequest.getName());
    patient.setEmail(patientRequest.getEmail());
    patient.setAddress(patientRequest.getAddress());
    patient.setDateOfBirth(LocalDate.parse(patientRequest.getDateOfBirth()));
    patient.setRegistered_date(LocalDate.parse(patientRequest.getRegisteredDate()));
    return patient;
  }

}
