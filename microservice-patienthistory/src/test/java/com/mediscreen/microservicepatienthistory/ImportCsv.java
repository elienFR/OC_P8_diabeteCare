package com.mediscreen.microservicepatienthistory;

import com.mediscreen.microservicepatienthistory.service.PatientHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ImportCsv {

  @Autowired
  private PatientHistoryService patientHistoryService;


  @Test
  public void importCsv(){
    patientHistoryService.insertCsv();
  }

}
