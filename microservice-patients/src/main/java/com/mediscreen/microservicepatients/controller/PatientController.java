package com.mediscreen.microservicepatients.controller;

import com.mediscreen.microservicepatients.model.DTO.PatientDTO;
import com.mediscreen.microservicepatients.model.Gender;
import com.mediscreen.microservicepatients.model.Patient;
import com.mediscreen.microservicepatients.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/" + "${api.ver}" + "/patient")
public class PatientController {

  @Autowired
  private PatientService patientService;

  @GetMapping("/all")
  public List<PatientDTO> getAll() {
    return patientService.getAll();
  }

  @PostMapping("/add")
  public PatientDTO insert(
    @RequestParam("family") String lastname,
    @RequestParam("given") String firstname,
    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob,
    @RequestParam("sex") Gender gender,
    @RequestParam("address") String addressNumberAndStreet,
    @RequestParam String phone) {

    return patientService.insert(lastname, firstname, dob, gender, addressNumberAndStreet, phone);
  }

  public PatientDTO insert(
    @RequestBody PatientDTO patientDTO) {
    return patientService.insert(patientDTO);
  }

}
