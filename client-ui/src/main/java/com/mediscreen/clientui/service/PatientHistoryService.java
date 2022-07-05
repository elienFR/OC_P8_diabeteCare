package com.mediscreen.clientui.service;


import com.mediscreen.clientui.model.beans.PatientHistory;
import com.mediscreen.clientui.model.utils.layout.Paged;
import com.mediscreen.clientui.proxy.MicroservicePatientsHistoryGatewayProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class PatientHistoryService {

  private final static Logger LOGGER = LogManager.getLogger(PatientHistoryService.class);

  @Autowired
  private MicroservicePatientsHistoryGatewayProxy patientsHistoryProxy;


  public Paged<PatientHistory> findByPatIdPage(String patId, Integer pageNum, Integer pageSize) {
    LOGGER.info("Retrieving patient notes' page : " +
      "\npatId=" + patId + ", pageNum=" + pageNum + ", pageSize=" + pageSize + ".");

    Paged<PatientHistory> patientHistoryPage = patientsHistoryProxy
      .findByPatIdPaged(patId, pageNum, pageSize);



    LOGGER.info("Notes' page received !");

    if(patientHistoryPage.getPage().isEmpty()){
      LOGGER.warn("But it does not contain any note.");
    }

    return patientHistoryPage;
  }

}
