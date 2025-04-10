package dev.alexgiou.patientservice.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  // Spring Boot will call this method when the specified User object is invalid.
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(error -> {
      errors.put(error.getField(), error.getDefaultMessage());
    });

    return ResponseEntity.badRequest().body(errors);
  }

  // Spring Boot will call this method when the specified EmailAlreadyExistsException is thrown.
  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(
      EmailAlreadyExistsException ex) {

    log.warn("Email address already exists {}", ex.getMessage());

    Map<String, String> errors = new HashMap<>();
    errors.put("message", "Email address already exists.");
    return ResponseEntity.badRequest().body(errors);
  }

  // Spring Boot will call this method when the specified PatientNotFoundException is thrown.
  @ExceptionHandler(PatientNotFoundException.class)
  public ResponseEntity<Map<String, String>> handlePatientNotFoundException(
      PatientNotFoundException ex) {

    log.warn("Patient not found {}", ex.getMessage());

    Map<String, String> errors = new HashMap<>();
    errors.put("message", "Patient not found.");
    return ResponseEntity.badRequest().body(errors);
  }

}

