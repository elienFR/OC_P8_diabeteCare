package com.mediscreen.microservicereport.service;

import com.mediscreen.microservicereport.model.Patient;
import com.mediscreen.microservicereport.proxy.MicroservicePatientProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Service
public class PatientService {

  @Autowired
  private MicroservicePatientProxy microservicePatientProxy;

  public Patient getPatient(String patId) {
    return microservicePatientProxy.getPatient(patId);
  }

  /**
   * This method give a patient age.
   *
   * @param patient is the patient you want the age
   * @return an integer of the patient's age
   */
  public Integer getAge(Patient patient) {
    return Period.between(patient.getBirthdate().toLocalDate(), LocalDate.now()).getYears();
  }

}
