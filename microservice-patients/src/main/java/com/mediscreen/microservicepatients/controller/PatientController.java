package com.mediscreen.microservicepatients.controller;

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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api" + "/${api.ver}" + "/patient")
//@RequestMapping("/patient")
public class PatientController {

  @Autowired
  private PatientService patientService;

  @GetMapping("/all")
  public List<PatientDTO> getAll() {
    return patientService.getAll();
  }

  @GetMapping("/patient")
  public ResponseEntity<PatientDTO> findByLastnameAndFirstnameAndBirthdate(
    @NotBlank(message = "Family is mandatory !")
    @RequestParam("family") String lastname,

    @NotBlank(message = "Given is mandatory !")
    @RequestParam("given") String firstname,

    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
    @RequestParam("dob") String birthdate
  ) {
    Optional<PatientDTO> optionalPatientDTO =
      patientService.findPatientDTOByLastnameAndFirstnameAndBirthdate(
        lastname,
        firstname,
        birthdate);
    if(optionalPatientDTO.isEmpty()){
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(optionalPatientDTO.get());
    }
  }

  @PostMapping("/add")
  public ResponseEntity<PatientDTO> insert(
    @NotBlank(message = "Family is mandatory !")
    @RequestParam("family") String lastname,

    @NotBlank(message = "Given is mandatory !")
    @RequestParam("given") String firstname,

    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
    @RequestParam("dob") String birthdate,

    @NotNull(message = "Gender is mandatory !")
    @RequestParam("sex") Gender gender,

    @NotBlank(message = "Address is mandatory !")
    @RequestParam("address") String addressNumberAndStreet,

    @RequestParam
    @Pattern(message = "must be a properly written US phone number, i.e : 123-456-7890",
      regexp = "^\\(?(\\d{3})\\)?[-.\\s]?(\\d{3})[-.\\s]?(\\d{4})$") String phone) {

    PatientDTO patientDTOAdded =
      patientService.insert(lastname, firstname, birthdate, gender, addressNumberAndStreet, phone);

    if (Objects.isNull(patientDTOAdded)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.status(HttpStatus.CREATED).body(patientDTOAdded);
    }

  }

  //  @PostMapping("/add")
  private PatientDTO insert(
    @RequestBody @Valid PatientDTO patientDTO) {
    return patientService.insert(patientDTO);
  }

  @PutMapping("/update")
  public ResponseEntity<PatientDTO> update(
    @NotBlank(message = "Family is mandatory !")
    @RequestParam("family") String lastname,

    @NotBlank(message = "Given is mandatory !")
    @RequestParam("given") String firstname,

    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
    @RequestParam("dob") String birthdate,

    @NotNull(message = "Gender is mandatory !")
    @RequestParam("sex") Gender gender,

    @NotBlank(message = "Address is mandatory !")
    @RequestParam("address") String addressNumberAndStreet,

    @RequestParam
    @Pattern(message = "must be a properly written US phone number, i.e : 123-456-7890",
      regexp = "^\\(?(\\d{3})\\)?[-.\\s]?(\\d{3})[-.\\s]?(\\d{4})$") String phone) {
    try {
      return ResponseEntity.ok(
        patientService
          .update(lastname, firstname, birthdate, gender, addressNumberAndStreet, phone)
      );
    } catch (PatientNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> delete(
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
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found, deletion abort !");
    }
  }
}
