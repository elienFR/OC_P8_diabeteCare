package com.mediscreen.microservicepatienthistory;

import com.mediscreen.microservicepatienthistory.model.PatientHistory;
import com.mediscreen.microservicepatienthistory.model.utils.layout.Paged;
import com.mediscreen.microservicepatienthistory.service.PatientHistoryService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PatientHistoryServiceTest {

  @Autowired
  private PatientHistoryService patientHistoryService;

  @Disabled
  @Test
  public void addTest() {
    for (int i = 0; i < 100; i++) {
      PatientHistory patientHistoryToInsert = new PatientHistory();
      patientHistoryToInsert.setPatId("1");
      patientHistoryToInsert.setNotes("a new notes number : " + i);
      patientHistoryService.insert(patientHistoryToInsert);
    }
  }

  @Disabled
  @Test
  public void findPatientHistory() {
    String givenPatientNumber = "1";
    List<PatientHistory> patientHistories =
      patientHistoryService.findByPatId(givenPatientNumber);
    patientHistories.forEach(System.out::println);

  }

  @Test
  public void findPatientHistoryPaged() {

//    Paged<PatientHistory> patientHistoryPage = patientHistoryService
//      .findByPatIdPaged(1,5,"1");
//    if(patientHistoryPage.getPage().isEmpty()){
//      System.out.println("patientHistoryPage is empty");
//    } else {
//      patientHistoryPage.getPage().forEach(System.out::println);
//    }
//
//    Paged<PatientHistory> patientHistoryPageEmpty = patientHistoryService
//      .findByPatIdPaged(1,5,"2");
//    if(patientHistoryPageEmpty.getPage().isEmpty()){
//      System.out.println("patientHistoryPageEmpty is empty");
//    } else {
//      patientHistoryPageEmpty.getPage().forEach(System.out::println);
//    }
  }

}
