package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.exceptions.AlreadyExistsException;
import com.mediscreen.clientui.exceptions.PatientNotFoundException;
import com.mediscreen.clientui.model.beans.PatientDTO;
import com.mediscreen.clientui.model.beans.PatientDTOForSearch;
import com.mediscreen.clientui.model.beans.PatientHistory;
import com.mediscreen.clientui.model.utils.layout.Paged;
import com.mediscreen.clientui.model.utils.layout.Paging;
import com.mediscreen.clientui.model.utils.layout.RestResponsePage;
import com.mediscreen.clientui.service.PatientHistoryService;
import com.mediscreen.clientui.service.PatientReportService;
import com.mediscreen.clientui.service.PatientService;
import feign.codec.DecodeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.UnknownContentTypeException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Controller
@RequestMapping("/patient")
public class PatientController {

  private static final Logger LOGGER = LogManager.getLogger(PatientController.class);

  @Autowired
  private PatientService patientService;
  @Autowired
  private PatientHistoryService patientHistoryService;
  @Autowired
  private PatientReportService patientReportService;

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

  @GetMapping("/search")
  public String find(PatientDTOForSearch patientDTOForSearch) {
    LOGGER.info("GET : /patient/search");
    return "patient/search";
  }

  @GetMapping("/search/validate")
  public String findValidation(@Valid PatientDTOForSearch patientDTOForSearch,
                               BindingResult result,
                               Model model) {
    LOGGER.info("POST : /patient/search/validate");
    LOGGER.info("Validating entries...");
    if (!result.hasErrors()) {
      LOGGER.info("No error found in entry.");
      try {
        model.addAttribute("patientDTO", patientService.getPatient(patientDTOForSearch));
        return "patient/found";
      } catch (PatientNotFoundException e) {
        model.addAttribute("patientDTO", new PatientDTO());
        model.addAttribute("patientNotFound", true);
        return "patient/found";
      } catch (Exception e) {
        e.printStackTrace();
        LOGGER.error("Une erreur est apparue...");
        return "../static/error/500";
      }
    }
    LOGGER.info("Error found, searching aborted.");
    return "patient/search";
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

    // TODO : vérifier que cela fonctionne avec les nouveau paramètres de variable th:object dans le document found.html et update.html

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

  @GetMapping("/notes")
  public String findNotes(String family,
                          String given,
                          String dob,
                          PatientDTO patientDTO,
                          @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                          @Min(value = 1)
                          @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                          PatientHistory notes,
                          Paged<PatientHistory> pagedNotes,
                          Model model) {
    LOGGER.info("GET : /patient/notes");
    try {
      // This code is used even if it looks redundant. It is used when you scroll from bars,
      // patient gender was not passed during this process so we reset patient in the code
      // so it works properly.
      if (Objects.isNull(patientDTO.getGender()) || Objects.isNull(patientDTO.getFamily()) || Objects.isNull(patientDTO.getDob()) || Objects.isNull(patientDTO.getGiven())) {
        patientDTO = patientService.getPatient(family, given, dob);
        model.addAttribute("patientDTO", patientDTO);
      }

      String patId = patientService.getId(patientDTO);
      model.addAttribute("patId", patId);
      model.addAttribute("notes", notes);
      model.addAttribute("diabetesAssessment", patientReportService
        .getAssess(Integer.parseInt(patId))
        .name()
      );

      pagedNotes = patientHistoryService.findByPatIdPage(patId, pageNum, pageSize);
      model.addAttribute("pagedNotes", pagedNotes);

      return "patient/notes";
    } catch (PatientNotFoundException pnfe) {
      //Handle when a patient has not been found
      LOGGER.warn("patient has not been found.");
      model.addAttribute("patientDTO", new PatientDTO());
      model.addAttribute("patientNotFound", true);
      return "patient/found";
    } catch (DecodeException e) {
      // This code part is here to handle the problem about feign exception when feign does not
      // handle JSON deserialization properly for null response from microservicepatient while
      // trying to get a patient history paged.
      LOGGER.warn("No notes were found");
      pagedNotes = new Paged<>();
      pagedNotes.setPage(new RestResponsePage<>());
      pagedNotes.setPaging(new Paging());
      model.addAttribute("pagedNotes", pagedNotes);
      return "patient/notes";
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @PostMapping("/notes/validate")
  public String postNotes(@Valid PatientHistory notes,
                          BindingResult results,
                          PatientDTO patientDTO,
                          String patId,
                          @Min(value = 1)
                          @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                          @Min(value = 1)
                          @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                          Model model) {
    LOGGER.info("POST : /notes/validate...");
    model.addAttribute("patId", patId);

    if (!results.hasErrors()) {
      LOGGER.info("No error found. Saving notes about patient : " + patientDTO +
        ", and notes : " + notes + "...");
      PatientHistory savedNotes = patientHistoryService.insert(notes);
      LOGGER.info("Saving successful of note : " + savedNotes);

      Paged<PatientHistory> pagedNotes = patientHistoryService.findByPatIdPage(patId, pageNum, pageSize);
      model.addAttribute("notes", new PatientHistory());
      model.addAttribute("pagedNotes", pagedNotes);
      model.addAttribute("diabetesAssessment", patientReportService
        .getAssess(Integer.parseInt(patId))
        .name()
      );
      return "patient/notes";
    }
    Paged<PatientHistory> pagedNotes = patientHistoryService.findByPatIdPage(patId, pageNum, pageSize);
    model.addAttribute("pagedNotes", pagedNotes);
    model.addAttribute("notes", notes);
    model.addAttribute("diabetesAssessment", patientReportService
      .getAssess(Integer.parseInt(patId))
      .name()
    );
    LOGGER.error("Errors found. No notes were saved.");
    return "patient/notes";
  }

}
