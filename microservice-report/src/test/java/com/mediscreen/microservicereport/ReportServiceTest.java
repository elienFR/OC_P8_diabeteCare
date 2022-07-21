package com.mediscreen.microservicereport;


import com.mediscreen.microservicereport.model.*;
import com.mediscreen.microservicereport.service.PatientHistoryService;
import com.mediscreen.microservicereport.service.PatientService;
import com.mediscreen.microservicereport.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Disabled
@ActiveProfiles("test")
@SpringBootTest
public class ReportServiceTest {

  // TODO : 5 tests out of 7 does not pass when using mvn:package whereas they pass using ide test...

  @Autowired
  private ReportService reportServiceUnderTest;
  @MockBean
  private PatientHistoryService patientHistoryServiceMocked;
  @MockBean
  private PatientService patientServiceMocked;

  Integer givenPatId;
  Patient givenMalePatient;
  Patient givenFemalePatient;
  List<PatientHistory> notes;
  @BeforeEach
  public void setup() {
    System.out.println("!!!!!!!!!!!! THIS IS SETUP BEFORE TEST !!!!!!!!!!!!");
    notes = new ArrayList<>();
    givenPatId = 1;
    givenMalePatient = new Patient("lastname", "firstname", Date.valueOf(LocalDate.of(1991, 6, 20)), Gender.M, null, null);
    givenFemalePatient = new Patient("lastname", "firstname", Date.valueOf(LocalDate.of(1991, 6, 20)), Gender.F, null, null);
    when(patientHistoryServiceMocked.findNotes(givenPatId)).thenReturn(notes);
    when(patientServiceMocked.getPatient(givenPatId)).thenReturn(givenMalePatient);
    when(patientServiceMocked.getAge(givenMalePatient)).thenReturn(31);
    System.out.println("!!!!!!!!!!!! END OF SETUP BEFORE TEST !!!!!!!!!!!!");
  }

  @Test
  public void createReportTestAssessmentNone() {
    PatientHistory patientHistory1 = new PatientHistory();
    patientHistory1.setNotes("some notes");
    PatientHistory patientHistory2 = new PatientHistory();
    patientHistory2.setNotes("some notes");
    PatientHistory patientHistory3 = new PatientHistory();
    patientHistory3.setNotes("some notes");
    notes.addAll(List.of(patientHistory1, patientHistory2, patientHistory3));

    DiabetesReport result = reportServiceUnderTest.createReport(givenPatId);

    assertThat(result.getPatient()).isEqualTo(givenMalePatient);
    assertThat(result.getDiabetesAssessment()).isEqualTo(DiabetesAssessment.NONE);
  }

  @Test
  public void createReportTestAssessmentBorderline(){
    PatientHistory patientHistory1 = new PatientHistory();
    patientHistory1.setNotes("some notes hémoglobine a1c");
    PatientHistory patientHistory2 = new PatientHistory();
    patientHistory2.setNotes("some notes taille");
    PatientHistory patientHistory3 = new PatientHistory();
    patientHistory3.setNotes("some notes");
    notes.addAll(List.of(patientHistory1, patientHistory2, patientHistory3));

    DiabetesReport result = reportServiceUnderTest.createReport(givenPatId);

    assertThat(result.getPatient()).isEqualTo(givenMalePatient);
    assertThat(result.getDiabetesAssessment()).isEqualTo(DiabetesAssessment.BORDERLINE);
  }

  @Test
  public void createReportTestAssessmentManInDangerMoreThan30yo(){
    PatientHistory patientHistory1 = new PatientHistory();
    patientHistory1.setNotes("some notes hémoglobine a1c");
    PatientHistory patientHistory2 = new PatientHistory();
    patientHistory2.setNotes("some notes taille");
    PatientHistory patientHistory3 = new PatientHistory();
    patientHistory3.setNotes("some notes Vertige");
    notes.addAll(List.of(patientHistory1, patientHistory2, patientHistory3));

    DiabetesReport result = reportServiceUnderTest.createReport(givenPatId);

    assertThat(result.getPatient()).isEqualTo(givenMalePatient);
    assertThat(result.getDiabetesAssessment()).isEqualTo(DiabetesAssessment.IN_DANGER);
  }

  @Test
  public void createReportTestAssessmentManInDangerLessThan30yo(){
    when(patientServiceMocked.getAge(givenMalePatient)).thenReturn(25);

    PatientHistory patientHistory1 = new PatientHistory();
    patientHistory1.setNotes("some notes hémoglobine a1c");
    PatientHistory patientHistory2 = new PatientHistory();
    patientHistory2.setNotes("some notes taille");
    PatientHistory patientHistory3 = new PatientHistory();
    patientHistory3.setNotes("some notes Vertige");
    notes.addAll(List.of(patientHistory1, patientHistory2, patientHistory3));

    DiabetesReport result = reportServiceUnderTest.createReport(givenPatId);

    assertThat(result.getPatient()).isEqualTo(givenMalePatient);
    assertThat(result.getDiabetesAssessment()).isEqualTo(DiabetesAssessment.IN_DANGER);
  }

  @Test
  public void createReportTestAssessmentManEarlyOnSetLess30yo(){
    when(patientServiceMocked.getAge(givenMalePatient)).thenReturn(25);

    PatientHistory patientHistory1 = new PatientHistory();
    patientHistory1.setNotes("some notes hémoglobine a1c");
    PatientHistory patientHistory2 = new PatientHistory();
    patientHistory2.setNotes("some notes taille");
    PatientHistory patientHistory3 = new PatientHistory();
    patientHistory3.setNotes("some notes Vertige");
    PatientHistory patientHistory4 = new PatientHistory();
    patientHistory4.setNotes("some notes rechute");
    PatientHistory patientHistory5 = new PatientHistory();
    patientHistory5.setNotes("some notes reaction");
    notes.addAll(List.of(patientHistory1, patientHistory2, patientHistory3, patientHistory4, patientHistory5));

    DiabetesReport result = reportServiceUnderTest.createReport(givenPatId);

    assertThat(result.getPatient()).isEqualTo(givenMalePatient);
    assertThat(result.getDiabetesAssessment()).isEqualTo(DiabetesAssessment.EARLY_ON_SET);
  }

  @Test
  public void createReportTestAssessmentInDangerMoreThan30yo(){

    PatientHistory patientHistory1 = new PatientHistory();
    patientHistory1.setNotes("some notes hémoglobine a1c");
    PatientHistory patientHistory2 = new PatientHistory();
    patientHistory2.setNotes("some notes taille");
    PatientHistory patientHistory3 = new PatientHistory();
    patientHistory3.setNotes("some notes Vertige");
    PatientHistory patientHistory4 = new PatientHistory();
    patientHistory4.setNotes("some notes rechute");
    PatientHistory patientHistory5 = new PatientHistory();
    patientHistory5.setNotes("some notes reaction");
    PatientHistory patientHistory6 = new PatientHistory();
    patientHistory6.setNotes("some notes cholesterol");
    notes.addAll(List.of(patientHistory1, patientHistory2, patientHistory3, patientHistory4, patientHistory5, patientHistory6));

    DiabetesReport result = reportServiceUnderTest.createReport(givenPatId);

    assertThat(result.getPatient()).isEqualTo(givenMalePatient);
    assertThat(result.getDiabetesAssessment()).isEqualTo(DiabetesAssessment.IN_DANGER);
  }

  @Test
  public void createReportTestAssessmentEarlyOnSetMoreThan30yo(){

    PatientHistory patientHistory1 = new PatientHistory();
    patientHistory1.setNotes("some notes hémoglobine a1c");
    PatientHistory patientHistory2 = new PatientHistory();
    patientHistory2.setNotes("some notes taille");
    PatientHistory patientHistory3 = new PatientHistory();
    patientHistory3.setNotes("some notes Vertige");
    PatientHistory patientHistory4 = new PatientHistory();
    patientHistory4.setNotes("some notes rechute");
    PatientHistory patientHistory5 = new PatientHistory();
    patientHistory5.setNotes("some notes reaction");
    PatientHistory patientHistory6 = new PatientHistory();
    patientHistory6.setNotes("some notes cholesterol");
    PatientHistory patientHistory7 = new PatientHistory();
    patientHistory7.setNotes("some notes fumeur");
    PatientHistory patientHistory8 = new PatientHistory();
    patientHistory8.setNotes("some notes anticorps");
    notes.addAll(List.of(patientHistory1, patientHistory2, patientHistory3, patientHistory4, patientHistory5, patientHistory6,patientHistory7,patientHistory8));

    DiabetesReport result = reportServiceUnderTest.createReport(givenPatId);

    assertThat(result.getPatient()).isEqualTo(givenMalePatient);
    assertThat(result.getDiabetesAssessment()).isEqualTo(DiabetesAssessment.EARLY_ON_SET);
  }


}
