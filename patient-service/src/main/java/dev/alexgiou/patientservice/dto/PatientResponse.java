package dev.alexgiou.patientservice.dto;

import lombok.Data;

@Data
public class PatientResponse {

  private String id;
  private String name;
  private String email;
  private String address;
  private String dateOfBirth;
}
