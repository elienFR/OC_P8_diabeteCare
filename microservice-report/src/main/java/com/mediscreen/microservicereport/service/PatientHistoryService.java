package com.mediscreen.microservicereport.service;

import com.mediscreen.microservicereport.model.PatientHistory;
import com.mediscreen.microservicereport.proxy.MicroservicePatientHistoryProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientHistoryService {

  private static final Logger LOGGER = LogManager.getLogger(PatientHistoryService.class);

  @Autowired
  private MicroservicePatientHistoryProxy microservicePatientHistoryProxy;

  public List<PatientHistory> findNotes(Integer patId) {
    LOGGER.info("Contacting proxy to retrieve notes from patient Id " + patId + ".");
    return microservicePatientHistoryProxy.find(patId.toString());
  }

  public static List<String> convertToNotes(List<PatientHistory> patientHistories) {
    LOGGER.info("Converting patient histories list to a list of patient's notes...");
    return patientHistories.stream().map(PatientHistory::getNotes).collect(Collectors.toList());
  }

}
