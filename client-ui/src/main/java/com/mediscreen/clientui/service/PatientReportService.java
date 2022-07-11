package com.mediscreen.clientui.service;

import com.mediscreen.clientui.model.beans.DiabetesAssessment;
import com.mediscreen.clientui.proxy.MicroserviceReportGatewayProxy;
import lombok.extern.java.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientReportService {

  private static final Logger LOGGER = LogManager.getLogger(PatientReportService.class);

  @Autowired
  private MicroserviceReportGatewayProxy microserviceReportGatewayProxy;

  public DiabetesAssessment getAssess(Integer patId) {
    LOGGER.info("Requesting assessment from patient id : " + patId + ".");
    return microserviceReportGatewayProxy.requestAssess(patId.toString());
  }

}
