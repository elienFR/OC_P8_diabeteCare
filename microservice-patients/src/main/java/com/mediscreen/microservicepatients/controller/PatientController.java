package com.mediscreen.microservicepatients.controller;

import com.mediscreen.microservicepatients.exceptions.AlreadyExistsException;
import com.mediscreen.microservicepatients.exceptions.PatientNotFoundException;
import com.mediscreen.microservicepatients.model.DTO.PatientDTO;
import com.mediscreen.microservicepatients.model.Gender;
import com.mediscreen.microservicepatients.service.PatientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api" + "/${api.ver}" + "/patient")
public class PatientController {

  private static final Logger LOGGER = LogManager.getLogger(PatientController.class);

  @Autowired
  private PatientService patientService;

  @Value("${api.ver}")
  String apiVer;

  @GetMapping("/all")
  public List<PatientDTO> getAll() {
    LOGGER.info("GET : /api/" + apiVer + "/patient/all");
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

  @GetMapping("/{id}")
  public ResponseEntity<Object> findById(@PathVariable("id") Integer patId) {
    if (patId != null) {
      try {
        return ResponseEntity.ok(patientService.findById(patId));
      } catch (PatientNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      }
    }
    return ResponseEntity.badRequest().body("Wrong parameters !");
  }

  @GetMapping("/patient")
  public ResponseEntity<Object> findByLastnameAndFirstnameAndBirthdate(
    @NotBlank(message = "Family is mandatory !")
    @RequestParam("family") String lastname,

    @NotBlank(message = "Given is mandatory !")
    @RequestParam("given") String firstname,

    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
    @RequestParam("dob") String birthdate) {
    LOGGER.info("GET : /api/" + apiVer + "/patient/patient?family="
      + lastname + "&given=" + firstname + "&dob=" + birthdate);
    Optional<PatientDTO> optionalPatientDTO =
      patientService.findPatientDTOByLastnameAndFirstnameAndBirthdate(
        lastname,
        firstname,
        birthdate);
    if (optionalPatientDTO.isEmpty()) {
      LOGGER.warn("Displaying 404 not found...");
      return patientNotFound();
    }
    LOGGER.info("Displaying patient info...");
    return ResponseEntity.ok(optionalPatientDTO.get());
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
    LOGGER.info("POST : /api/" + apiVer + "/patient/add");
    try {
      PatientDTO patientDTOAdded =
        patientService.insert(lastname, firstname, birthdate, gender, addressNumberAndStreet, phone);
      LOGGER.info("Displaying 201 CREATED + added patient info...");
      return ResponseEntity.status(HttpStatus.CREATED).body(patientDTOAdded);
    } catch (AlreadyExistsException e) {
      e.printStackTrace();
      LOGGER.info("Displaying 409 CONFLICT...");
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
    LOGGER.info("PUT : /api/" + apiVer + "/patient/update/validate");
    try {
      LOGGER.info("Trying to update patient.");
      return ResponseEntity.ok(
        patientService
          .update(lastname, firstname, birthdate, gender, addressNumberAndStreet, phone)
      );
    } catch (PatientNotFoundException e) {
      e.printStackTrace();
      LOGGER.warn("Displaying 404 not found...");
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
    @RequestParam("dob") String birthdate) {
    LOGGER.info("DELETE : /api/" + apiVer + "/patient/delete");
    if (patientService.delete(lastname, firstname, birthdate)) {
      return ResponseEntity.ok("Deletion successful !");
    } else {
      LOGGER.warn("Displaying 404 not found...");
      return patientNotFound();
    }
  }

  @GetMapping("/getId")
  ResponseEntity<Object> getId(@NotBlank(message = "Family is mandatory !")
                               @RequestParam("family") String lastname,
                               @NotBlank(message = "Given is mandatory !")
                               @RequestParam("given") String firstname,
                               @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
                               @RequestParam("dob") String birthdate) {
    LOGGER.info("GET : /findId" +
      "?family=" + lastname +
      "&given=" + firstname +
      "&dob" + birthdate);
    Integer patId = patientService.getId(lastname, firstname, birthdate);
    if (Objects.isNull(patId)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such patient found");
    }
    LOGGER.info("Displaying patient's id...");
    return ResponseEntity.ok(patId);
  }

  /**
   * This method is NOT TO BE USED. It is only here to import a csv file from patienthistory.
   * Normally two or more patients can have the same lastname. As a consequence you cannot find one
   * patient associated to one lastname.
   * But the given csv file is constructed so that one lastname is associated with ONLY one patient.
   * So we constructed this method to import such file ONLY. not for other purpose.
   *
   * @param lastname a given lastname
   * @return the id associated to the lastname
   */
  @GetMapping("/id")
  public Integer getIdFromFamily(@RequestParam("family") String lastname){
    return patientService.getIdFromFamily(lastname);
  }
}
