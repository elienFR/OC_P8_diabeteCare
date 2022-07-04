package com.mediscreen.microservicepatienthistory.service;

import com.mediscreen.microservicepatienthistory.model.PatientHistory;
import com.mediscreen.microservicepatienthistory.repository.PatientHistoryRepository;
import org.apache.juli.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientHistoryService {

  private final static Logger LOGGER = LogManager.getLogger(PatientHistoryService.class);

  @Autowired
  private PatientHistoryRepository patientHistoryRepository;

  public PatientHistory insert(PatientHistory patientHistory) {
    LOGGER.info("Inserting " + patientHistory + " into database.");
    return patientHistoryRepository.insert(patientHistory);
  }

  public Optional<List<PatientHistory>> findByPatId(String patId) {
    Optional<List<PatientHistory>> optionalPatientHistories =
      patientHistoryRepository.findByPatId(patId);
    if (optionalPatientHistories.isPresent()) {
      if(!optionalPatientHistories.get().isEmpty()){
        return optionalPatientHistories;
      }
    }
    return Optional.empty();
  }
}
