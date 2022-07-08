package com.mediscreen.microservicereport.service;

import com.mediscreen.microservicereport.model.PatientHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientHistoryService {

  @Autowired
  private PatientService patientService;

  public List<PatientHistory> getRecord(String patId) {
    return null;
  }

}
