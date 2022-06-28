package com.mediscreen.microservicepatients.controller;

import com.mediscreen.microservicepatients.model.DTO.PatientDTO;
import com.mediscreen.microservicepatients.model.Gender;
import com.mediscreen.microservicepatients.model.Patient;
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
import java.util.Date;
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
  public PatientDTO findByLastnameAndFirstnameAndBirthDate(
    @NotBlank(message = "Family is mandatory !")
    @RequestParam("family") String lastname,

    @NotBlank(message = "Given is mandatory !")
    @RequestParam("given") String firstname,

    @PastOrPresent(message = "Date of birth must be past or present !")
    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob
  ) {
    return patientService.findByLastnameAndFirstnameAndBirthDate(lastname, firstname, dob);
  }

  @PostMapping("/add")
  public PatientDTO insert(
    @NotBlank(message = "Family is mandatory !")
    @RequestParam("family") String lastname,

    @NotBlank(message = "Given is mandatory !")
    @RequestParam("given") String firstname,

    @PastOrPresent(message = "Date of birth must be past or present !")
    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob,

    @NotNull(message = "Gender is mandatory !")
    @RequestParam("sex") Gender gender,

    @NotBlank(message = "Address is mandatory !")
    @RequestParam("address") String addressNumberAndStreet,

    @RequestParam
    @Pattern(message = "must be a properly written US phone number, i.e : 123-456-7890",
      regexp = "^\\(?(\\d{3})\\)?[-.\\s]?(\\d{3})[-.\\s]?(\\d{4})$") String phone) {
    return patientService.insert(lastname, firstname, dob, gender, addressNumberAndStreet, phone);
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

    @PastOrPresent(message = "Date of birth must be past or present !")
    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob
  ){
    patientService.delete(
      findByLastnameAndFirstnameAndBirthDate(lastname, firstname, dob)
    );
    return "delete successful !";
  }


}
