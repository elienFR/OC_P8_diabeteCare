package com.mediscreen.microservicepatienthistory.controller;

import com.mediscreen.microservicepatienthistory.model.PatientHistory;
import com.mediscreen.microservicepatienthistory.service.PatientHistoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api" + "/${api.ver}" + "/patHistory")
public class PatientHistoryController {

  private final static Logger LOGGER = LogManager.getLogger(PatientHistoryController.class);

  @Autowired
  private PatientHistoryService patientHistoryService;

  @GetMapping("/find")
  public ResponseEntity<Object> find(@RequestParam String patId) {
    LOGGER.info("GET : /find?patId=" + patId + " ...");
    Optional<List<PatientHistory>> patientNotes = patientHistoryService.findByPatId(patId);
    if (patientNotes.isPresent()) {
      return ResponseEntity.ok(patientNotes.get());
    }
    LOGGER.warn("No notes found for patientId : " + patId);
    return ResponseEntity.ok("No notes found for patientId : " + patId);
  }

  @PostMapping("/add")
  public ResponseEntity<Object> add(@Valid @RequestBody PatientHistory patientHistory,
                                    BindingResult results) {
    LOGGER.info("POST : /patHistory/add...");
    if (!results.hasErrors()) {
      LOGGER.info("Trying to save : " + patientHistory);
      return ResponseEntity.ok(patientHistoryService.insert(patientHistory));
    }
    LOGGER.error("There were error(s) in this post... Nothing was saved");
    return ResponseEntity
      .badRequest()
      .body(
        results
          .getAllErrors()
          .stream()
          .map(ObjectError::getDefaultMessage)
          .collect(Collectors.toList())
      );
  }

}
