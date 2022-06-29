package com.mediscreen.microservicepatients.controller;

import com.mediscreen.microservicepatients.controller.exceptions.AlreadyExistException;
import com.mediscreen.microservicepatients.controller.exceptions.PatientNotFoundException;
import com.mediscreen.microservicepatients.model.DTO.PatientDTO;
import com.mediscreen.microservicepatients.model.Gender;
import com.mediscreen.microservicepatients.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.net.URI;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api" + "/${api.ver}" + "/patient")
public class PatientController {

  @Autowired
  private PatientService patientService;

  @GetMapping("/all")
  public List<PatientDTO> getAll() {
    return patientService.getAll();
  }

  /**
   * A quick method to mutualise response status when a patient is not found.
   *
   * @return a response entity with error code 404 that says patient has not been found.
   */
  private ResponseEntity<Object> patientNotFound() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The patient has not been found.");
  }

  @GetMapping("/patient")
  public ResponseEntity<Object> findByLastnameAndFirstnameAndBirthdate(
    @NotBlank(message = "Family is mandatory !")
    @RequestParam("family") String lastname,

    @NotBlank(message = "Given is mandatory !")
    @RequestParam("given") String firstname,

    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
    @RequestParam("dob") String birthdate) {
    Optional<PatientDTO> optionalPatientDTO =
      patientService.findPatientDTOByLastnameAndFirstnameAndBirthdate(
        lastname,
        firstname,
        birthdate);
    if (optionalPatientDTO.isEmpty()) {
      return patientNotFound();
    } else {
      return ResponseEntity.ok(optionalPatientDTO.get());
    }
  }

  @PostMapping("/add")
  public ResponseEntity<Object> insert(
    @NotBlank(message = "Family is mandatory !")
    @RequestParam("family") String lastname,

    @NotBlank(message = "Given is mandatory !")
    @RequestParam("given") String firstname,

    @Pattern(regexp = "^(?:(\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]))|)$")
    @RequestParam("dob") String birthdate,

    @NotNull(message = "Gender is mandatory !")
    @RequestParam("sex") Gender gender,

    @RequestParam("address") String addressNumberAndStreet,

    // The pattern corresponds to US phone number with a number 'n' so it has to correspond to
    // 'nnn-nnn-nnnn' or the pattern has to correspond to an empty string.
    @RequestParam("phone")
    @Pattern(message = "must be a properly written US phone number, i.e : 123-456-7890",
      regexp = "^(?:(\\(?(\\d{3})\\)?[-.\\s]?(\\d{3})[-.\\s]?(\\d{4}))|)$") String phone) {
    try {
      PatientDTO patientDTOAdded =
        patientService.insert(lastname, firstname, birthdate, gender, addressNumberAndStreet, phone);
      return ResponseEntity.status(HttpStatus.CREATED).body(patientDTOAdded);
    } catch (AlreadyExistException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.CONFLICT).body("This patient already exists.");
    }
  }

  @PutMapping("/update")
  public ResponseEntity<Object> update(
    @NotBlank(message = "Family is mandatory !")
    @RequestParam("family") String lastname,

    @NotBlank(message = "Given is mandatory !")
    @RequestParam("given") String firstname,

    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
    @RequestParam("dob") String birthdate,

    @NotNull(message = "Gender is mandatory !")
    @RequestParam("sex") Gender gender,

    @RequestParam("address") String addressNumberAndStreet,

    // The pattern corresponds to US phone number with a number 'n' so it has to correspond to
    // 'nnn-nnn-nnnn' or the pattern has to correspond to an empty string.
    @RequestParam("phone")
    @Pattern(message = "must be a properly written US phone number, i.e : 123-456-7890",
      regexp = "^(?:(\\(?(\\d{3})\\)?[-.\\s]?(\\d{3})[-.\\s]?(\\d{4}))|)$") String phone) {
    try {
      return ResponseEntity.ok(
        patientService
          .update(lastname, firstname, birthdate, gender, addressNumberAndStreet, phone)
      );
    } catch (PatientNotFoundException e) {
      e.printStackTrace();
      return patientNotFound();
    }
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> delete(
    @NotBlank(message = "Family is mandatory !")
    @RequestParam("family") String lastname,

    @NotBlank(message = "Given is mandatory !")
    @RequestParam("given") String firstname,

    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
    @RequestParam("dob") String birthdate
  ) {
    if (patientService.delete(lastname, firstname, birthdate)) {
      return ResponseEntity.ok("Deletion successful !");
    } else {
      return patientNotFound();
    }
  }
}
