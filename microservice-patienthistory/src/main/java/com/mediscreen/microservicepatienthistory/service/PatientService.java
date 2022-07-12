package com.mediscreen.microservicepatienthistory.service;

import com.mediscreen.microservicepatienthistory.proxy.MicroservicePatientProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

  @Autowired
  private MicroservicePatientProxy microservicePatientProxy;

  public Integer getIdFromLastName(String lastname) {
    return microservicePatientProxy.getIdFromFamily(lastname);
  }
}
