package com.mediscreen.clientui.service;

import com.mediscreen.clientui.controller.PatientController;
import com.mediscreen.clientui.exceptions.AlreadyExistsException;
import com.mediscreen.clientui.exceptions.PatientNotFoundException;
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

  public PatientDTO getPatient(String lastname, String firstname, String birthdate) {
    return microservicePatientsGatewayProxy.getPatient(lastname, firstname, birthdate);
  }

  public PatientDTO getPatient(@Valid PatientDTOForSearch patientDTO) {
    LOGGER.info("Getting PatientDTO from database...");
    try {
      return getPatient(
        patientDTO.getFamily(),
        patientDTO.getGiven(),
        patientDTO.getDob().toString()
      );
    } catch (PatientNotFoundException pnfe) {
      throw pnfe;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public PatientDTO save(@Valid PatientDTO patientDTO) throws AlreadyExistsException {
    LOGGER.info("Saving patient...");
    try {
      PatientDTO savedPatient = microservicePatientsGatewayProxy.insert(
        patientDTO.getFamily(),
        patientDTO.getGiven(),
        patientDTO.getDob().toString(),
        patientDTO.getGender(),
        patientDTO.getAddress(),
        patientDTO.getPhone()
      );
      LOGGER.info("Patient : " + savedPatient);
      return savedPatient;
    } catch (AlreadyExistsException aee) {
      LOGGER.error("Error caught : " + aee.getMessage());
      throw aee;
    } catch (Exception e) {
      LOGGER.error("Error caught : " + e.getMessage());
      e.printStackTrace();
      throw e;
    }
  }

  public PatientDTO updatePatient(String initialFamily,
                                  String initialGiven,
                                  String initialDob,
                                  PatientDTO patientDTOUpdated) throws PatientNotFoundException {
    try {
      LOGGER.info("Contacting microservicePatientsGatewayProxy for update with : \n" +
        "initialFamily : " + initialFamily + "\n" +
        "initialGiven : " + initialGiven + "\n" +
        "initialDob : " + initialDob + "\n" +
        "updated patient DTO : " + patientDTOUpdated.toString()
      );
      return microservicePatientsGatewayProxy.update(
        initialFamily,
        initialGiven,
        initialDob,
        patientDTOUpdated.getGender(),
        patientDTOUpdated.getAddress(),
        patientDTOUpdated.getPhone()
      );
    } catch (PatientNotFoundException pnfe) {
      LOGGER.error("the patient you are trying to update has not been found");
      pnfe.printStackTrace();
      throw pnfe;
    } catch (Exception e) {
      LOGGER.info("An error happened please refer to stack trace.");
      e.printStackTrace();
      throw e;
    }
  }

  public String getId(PatientDTO patientDTO) {
    LOGGER.info("Getting ID of patient " + patientDTO);
    String patId = microservicePatientsGatewayProxy.getId(
        patientDTO.getFamily(),
        patientDTO.getGiven(),
        patientDTO.getDob().toString())
      .toString();
    if (patId.isBlank()) {
      LOGGER.warn("No such patient found. Id returned is null.");
      return null;
    }
    LOGGER.info("Patient id is : patID=" + patId);
    return patId;
  }
}
