package com.mediscreen.clientui.service;

import com.mediscreen.clientui.controller.PatientController;
import com.mediscreen.clientui.model.beans.PatientDTO;
import com.mediscreen.clientui.model.beans.PatientDTOForSearch;
import com.mediscreen.clientui.proxy.MicroservicePatientsGatewayProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class PatientService {

  private static final Logger LOGGER = LogManager.getLogger(PatientService.class);

  @Autowired
  private MicroservicePatientsGatewayProxy microservicePatientsGatewayProxy;

  private PatientDTO getPatient(String lastname, String firstname, String birthdate) {
    return microservicePatientsGatewayProxy.getPatient(lastname, firstname, birthdate);
  }

  public PatientDTO getPatient(@Valid PatientDTOForSearch patientDTO) {
    LOGGER.info("Getting PatientDTO from database...");
    return getPatient(
      patientDTO.getFamily(),
      patientDTO.getGiven(),
      patientDTO.getDob().toString()
    );
  }
}
