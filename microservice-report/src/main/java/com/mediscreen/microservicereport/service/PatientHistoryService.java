package com.mediscreen.microservicereport.service;

import com.mediscreen.microservicereport.model.PatientHistory;
import com.mediscreen.microservicereport.proxy.MicroservicePatientHistoryProxy;
import feign.codec.DecodeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.UnknownContentTypeException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientHistoryService {

  private static final Logger LOGGER = LogManager.getLogger(PatientHistoryService.class);

  @Autowired
  private MicroservicePatientHistoryProxy microservicePatientHistoryProxy;

  /**
   * This method extract a list of patient histories from a patient id
   *
   * @param patId is the patient id of the patient from whom you want to extract a list of patient histories.
   * @return a list of patient histories
   */
  public List<PatientHistory> findNotes(Integer patId) {
    LOGGER.info("Contacting proxy to retrieve notes from patient Id " + patId + ".");
    try {
      return microservicePatientHistoryProxy.find(patId.toString());
    } catch (DecodeException e) {
      LOGGER.warn("Decode exception from feign (probably because no notes were associated with that patient).");
      LOGGER.warn("Returning an empty list of notes.");
      return new ArrayList<>();
    }

  }

  /**
   * This method convert a List of patient histories to a list of string corresponding to each notes of patient histories.
   *
   * @param patientHistories is the list of patient histories
   * @return a list of notes (String)
   */
  public static List<String> convertToNotes(List<PatientHistory> patientHistories) {
    LOGGER.info("Converting patient histories list to a list of patient's notes...");
    return patientHistories.stream().map(PatientHistory::getNotes).collect(Collectors.toList());
  }

}
