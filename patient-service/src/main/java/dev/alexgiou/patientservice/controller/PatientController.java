package dev.alexgiou.patientservice.controller;

import dev.alexgiou.patientservice.dto.PatientRequest;
import dev.alexgiou.patientservice.dto.PatientResponse;
import dev.alexgiou.patientservice.dto.validators.CreatePatientValidationGroup;
import dev.alexgiou.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
@Tag(name = "Patient", description = "API for patient management")
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    @Operation(summary = "Get all patients")
    public ResponseEntity<List<PatientResponse>> getPatients() {
        List<PatientResponse> patients = patientService.getAllPatients();
        return ResponseEntity.ok().body(patients);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a patient by ID")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable UUID id) {
        PatientResponse patient = patientService.getPatientById(id);
        return ResponseEntity.ok().body(patient);
    }

    @PostMapping
    @Operation(summary = "Create a new patient")
    public ResponseEntity<PatientResponse> createPatient(@Validated({Default.class,
            CreatePatientValidationGroup.class}) @RequestBody PatientRequest patientRequest) {

        PatientResponse patient = patientService.createPatient(patientRequest);
        return ResponseEntity.ok().body(patient);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing patient")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable UUID id,
                                                         @Validated({Default.class}) @RequestBody PatientRequest patientRequest) {

        PatientResponse patient = patientService.updatePatient(id, patientRequest);
        return ResponseEntity.ok().body(patient);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an existing patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
