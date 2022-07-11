package com.mediscreen.microservicereport.controller;

import com.mediscreen.microservicereport.exceptions.PatientNotFoundException;
import com.mediscreen.microservicereport.service.ReportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api" + "/${api.ver}" + "/assess")
public class ReportController {

  private static final Logger LOGGER = LogManager.getLogger(ReportController.class);

  @Autowired
  private ReportService reportService;


  @PostMapping("/id")
  public ResponseEntity<Object> requestReport(@NotBlank(message = "The patient id cannot be null")
                                              @RequestParam String patId) {

      LOGGER.info("Requesting report for patId : " + patId);
      try {
        return ResponseEntity.ok(reportService.displayReport(Integer.parseInt(patId)));
      } catch (PatientNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient has not been found");
      }catch (NumberFormatException e) {
        return ResponseEntity.badRequest().body("You made a bad request");
      }
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<Object> requestDiabetesReport(@NotBlank(message = "The patient id cannot be null")
                                                      @PathVariable("id") String patId) {
    LOGGER.info("Requesting diabetes report for patId : " + patId);
    try {
      return ResponseEntity.ok(reportService.createReport(Integer.parseInt(patId)));
    } catch (PatientNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient has not been found");
    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest().body("You made a bad request");
    }
  }

  @GetMapping("/assessOnly/{id}")
  public ResponseEntity<Object> requestAssess(@NotBlank(message = "The patient id cannot be null")
                                              @PathVariable("id") String patId) {
    LOGGER.info("Requesting diabetes report for patId : " + patId);
    try {
      return ResponseEntity.ok(reportService.createReport(Integer.parseInt(patId)).getDiabetesAssessment());
    } catch (PatientNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient has not been found");
    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest().body("You made a bad request");
    }
  }

}
