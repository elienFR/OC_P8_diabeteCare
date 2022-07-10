package com.mediscreen.microservicereport.service;

import com.mediscreen.microservicereport.model.Patient;
import com.mediscreen.microservicereport.proxy.MicroservicePatientProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Service
public class PatientService {

  private static final Logger LOGGER = LogManager.getLogger(PatientService.class);

  @Autowired
  private MicroservicePatientProxy microservicePatientProxy;

  public Patient getPatient(Integer patId) {
    LOGGER.info("Contacting proxy to get patient from patient Id : " + patId + ".");
    return microservicePatientProxy.findById(patId);
  }

  /**
   * This method give a patient age.
   *
   * @param patient is the patient you want the age
   * @return an integer of the patient's age
   */
  public Integer getAge(Patient patient) {
    LOGGER.info("Getting age from patient " + patient.toStringSmall() + ".");
    return Period.between(patient.getBirthdate().toLocalDate(), LocalDate.now()).getYears();
  }

}
