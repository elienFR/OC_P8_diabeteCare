package com.mediscreen.microservicepatienthistory.controller;

import com.mediscreen.microservicepatienthistory.model.PatientHistory;
import com.mediscreen.microservicepatienthistory.service.PatientHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/patHistory")
public class PatientHistoryController {

  @Autowired
  private PatientHistoryService patientHistoryService;

  @GetMapping("/all")
  public List<PatientHistory> getAll() {
    return null;
  }
  
  @PostMapping("/add")
  public PatientHistory add(PatientHistory patientHistory) {
    return null;
  }

}
