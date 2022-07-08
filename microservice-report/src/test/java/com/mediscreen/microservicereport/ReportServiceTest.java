package com.mediscreen.microservicereport;


import com.mediscreen.microservicereport.model.DiabetesAssessment;
import com.mediscreen.microservicereport.model.DiabetesReport;
import com.mediscreen.microservicereport.model.Patient;
import com.mediscreen.microservicereport.model.PatientHistory;
import com.mediscreen.microservicereport.service.PatientHistoryService;
import com.mediscreen.microservicereport.service.PatientService;
import com.mediscreen.microservicereport.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@SpringBootTest
public class ReportServiceTest {

  @Autowired
  private ReportService reportServiceUnderTest;
  @MockBean
  private PatientHistoryService patientHistoryServiceMocked;
  @MockBean
  private PatientService patientServiceMocked;

  @Test
  public void createReportTestAssessmentNone() {
    String givenPatId = "1";
    Patient givenPatient = new Patient();

    List<PatientHistory> notes = new ArrayList();

    when(patientHistoryServiceMocked.getRecord(givenPatId)).thenReturn(notes);
    when(patientServiceMocked.getPatient(givenPatId)).thenReturn(givenPatient);

    DiabetesReport result = reportServiceUnderTest.createReport(givenPatId);

    assertThat(result.getDiabetesAssessment()).isEqualTo(DiabetesAssessment.NONE);

  }

}
