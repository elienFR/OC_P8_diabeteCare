package com.mediscreen.clientui.service;

import com.mediscreen.clientui.model.beans.PatientDTO;
import com.mediscreen.clientui.proxy.MicroservicePatientsGatewayProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

  @Autowired
  private MicroservicePatientsGatewayProxy microservicePatientsGatewayProxy;

  private PatientDTO getPatient(String lastname, String firstname, String birthdate) {
    return microservicePatientsGatewayProxy.getPatient(lastname, firstname, birthdate);
  }

  public PatientDTO getPatient(PatientDTO patientDTO) {
    return getPatient(
      patientDTO.getFamily(),
      patientDTO.getGiven(),
      patientDTO.getDob().toString()
    );
  }
}
