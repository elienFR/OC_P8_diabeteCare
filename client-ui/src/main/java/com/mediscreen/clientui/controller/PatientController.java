package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.model.beans.PatientDTOForSearch;
import com.mediscreen.clientui.service.PatientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/patient")
public class PatientController {

  private static final Logger LOGGER = LogManager.getLogger(PatientController.class);

  @Autowired
  private PatientService patientService;

  @GetMapping("/search")
  public String find(PatientDTOForSearch patientDTOForSearch) {
    LOGGER.info("GET : /patient/search");
    return "patient/search";
  }

  @PostMapping("/search/validate")
  public String findValidation(@Valid PatientDTOForSearch patientDTOForSearch,
                               BindingResult result,
                               Model model) {
    LOGGER.info("POST : /patient/search/validate");
    LOGGER.info("Validating entries...");
    if (!result.hasErrors()) {
      LOGGER.info("No error found in entry.");

      //TODO : handle custom validation messages see https://www.baeldung.com/spring-custom-validation-message-source
      //TODO : handle 404 from feign here

      model.addAttribute("patientDTOFound", patientService.getPatient(patientDTOForSearch));
      return "patient/found";
    }
    LOGGER.info("Error found, searching aborted.");
    return "patient/search";
  }

  @GetMapping("/update")
  public String update() {
    return "patient/update";
  }


}
