package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.model.beans.Gender;
import com.mediscreen.clientui.model.beans.PatientDTO;
import com.mediscreen.clientui.model.beans.PatientHistory;
import com.mediscreen.clientui.model.utils.layout.Paged;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Date;

@Controller
public class TestController {

  // TODO : DELETE THIS CLASS FOR PRODUCTION VERSION

  private PatientDTO initPatient() {
    PatientDTO patientDTO = new PatientDTO();
    patientDTO.setFamily("Doe");
    patientDTO.setGiven("John");
    patientDTO.setPhone("123-456-7890");
    patientDTO.setAddress("1 camedown St");
    patientDTO.setGender(Gender.M);
    patientDTO.setDob(Date.valueOf("1993-10-16"));
    return patientDTO;
  }

  @GetMapping("/test")
  public String test(Model model){
    model.addAttribute("patientDTO", initPatient());
    model.addAttribute("notePages", new Paged<PatientHistory>());
    return "patient/notes";
  }

}
