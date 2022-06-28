package com.mediscreen.microservicepatients.controller;

import com.mediscreen.microservicepatients.model.DTO.PatientDTO;
import com.mediscreen.microservicepatients.model.Gender;
import com.mediscreen.microservicepatients.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.util.List;

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

  @GetMapping("/")
  public PatientDTO findByLastnameAndFirstnameAndBirthdate(
    @NotBlank(message = "Family is mandatory !")
    @RequestParam("family") String lastname,

    @NotBlank(message = "Given is mandatory !")
    @RequestParam("given") String firstname,

    @PastOrPresent(message = "Date of birth must be past or present !")
    @RequestParam("dob") @DateTimeFormat(pattern = "yyyy-MM-dd") String birthdate
  ) {
    return patientService.findPatientDTOByLastnameAndFirstnameAndBirthdate(lastname, firstname, birthdate).get();
  }

  @PostMapping("/add")
  public PatientDTO insert(
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
    return patientService.insert(lastname, firstname, birthdate, gender, addressNumberAndStreet, phone);
  }

  //  @PostMapping("/add")
  private PatientDTO insert(
    @RequestBody @Valid PatientDTO patientDTO) {
    return patientService.insert(patientDTO);
  }

  @DeleteMapping("/delete")
  public String delete(
    @NotBlank(message = "Family is mandatory !")
    @RequestParam("family") String lastname,

    @NotBlank(message = "Given is mandatory !")
    @RequestParam("given") String firstname,

    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
    @RequestParam("dob") String birthdate
  ) {
    if(patientService.delete(lastname, firstname, birthdate)){
      return "Deletion successful !";
    } else {
      return "Not found, deletion abort !";
    }
  }
}
