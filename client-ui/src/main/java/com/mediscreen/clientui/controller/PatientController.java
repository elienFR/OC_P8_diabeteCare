package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.exceptions.AlreadyExistsException;
import com.mediscreen.clientui.exceptions.PatientNotFoundException;
import com.mediscreen.clientui.model.beans.PatientDTO;
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
      try {
        model.addAttribute("patientDTOFound", patientService.getPatient(patientDTOForSearch));
        return "patient/found";
      } catch (PatientNotFoundException e) {
        model.addAttribute("patientDTOFound", new PatientDTO());
        model.addAttribute("patientNotFound", true);
        return "patient/found";
      } catch (Exception e) {
        return "../static/error/500";
      }
    }
    LOGGER.info("Error found, searching aborted.");
    return "patient/search";
  }

  @GetMapping("/add")
  public String add(PatientDTO patientDTO) {
    LOGGER.info("GET : /patient/add...");
    return "patient/add";
  }

  @PostMapping("/add/validate")
  public String addValidated(@Valid PatientDTO patientDTO,
                             BindingResult results,
                             Model model) {
    LOGGER.info("POST : /patient/add/validate...");
    LOGGER.info("Posted patient is \n" + patientDTO.toString());
    if (!results.hasErrors()) {
      try {
        PatientDTO patientSaved = patientService.save(patientDTO);
        model.addAttribute("patientDTOFound", patientSaved);
        return "/patient/added";
      } catch (AlreadyExistsException aee) {
        LOGGER.warn("patient already exists, it wont be saved.");
        model.addAttribute("patientAlreadyExists", true);
        return "/patient/add";
      } catch (Exception e) {
        e.printStackTrace();
        return "/patient/add";
      }
    }
    LOGGER.warn("Error found in patientDTO object...");
    results.getAllErrors().forEach(
      e ->
        LOGGER.warn(e.getDefaultMessage())

    );
    return "/patient/add";
  }

  @PostMapping("/update")
  public String updateFromSearch(PatientDTO patientDTO,
                                 Model model) {
    LOGGER.info("POST : /patient/update");
    model.addAttribute("initialFamily", patientDTO.getFamily());
    model.addAttribute("initialGiven", patientDTO.getGiven());
    model.addAttribute("initialDob", patientDTO.getDob().toString());
    return "patient/update";
  }

  @PostMapping("/update/validate")
  public String updateValidation(@Valid PatientDTO patientDTO,
                                 BindingResult results,
                                 String initialFamily,
                                 String initialGiven,
                                 String initialDob,
                                 Model model) {
    LOGGER.info("POST : /patient/update/validate");
    model.addAttribute("initialFamily", patientDTO.getFamily());
    model.addAttribute("initialGiven", patientDTO.getGiven());
    model.addAttribute("initialDob", patientDTO.getDob().toString());
    if (!results.hasErrors()) {
      try {
        LOGGER.info("Updating patient...");
        PatientDTO updatedPatient = patientService.updatePatient(
          initialFamily,
          initialGiven,
          initialDob,
          patientDTO
        );
        LOGGER.info("Patient successfully updated;");
        model.addAttribute("updateSuccessful", true);
        model.addAttribute("patientDTOFound", updatedPatient);
        return "patient/found";
      } catch (Exception e) {
        LOGGER.error("An error occurred during update...");
        e.printStackTrace();
      }
      LOGGER.error("An error occurred during update...");
      return "patient/update";
    }
    LOGGER.error("The patient provided could not pass validation tests...");
    return "patient/update";
  }


}
