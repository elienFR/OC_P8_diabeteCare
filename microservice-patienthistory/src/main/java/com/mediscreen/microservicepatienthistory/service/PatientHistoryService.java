package com.mediscreen.microservicepatienthistory.service;

import com.mediscreen.microservicepatienthistory.repository.PatientHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientHistoryService {

  @Autowired
  private PatientHistoryRepository patientHistoryRepository;



}
