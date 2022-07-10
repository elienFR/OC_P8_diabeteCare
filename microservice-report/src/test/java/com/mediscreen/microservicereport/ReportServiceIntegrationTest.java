package com.mediscreen.microservicereport;

import com.mediscreen.microservicereport.model.DiabetesAssessment;
import com.mediscreen.microservicereport.model.DiabetesReport;
import com.mediscreen.microservicereport.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReportServiceIntegrationTest {

  @Autowired
  private ReportService reportServiceUnderTest;

  @Test
  public void testNone() {

    DiabetesReport result = reportServiceUnderTest.createReport(1);

    assertThat(result.getPatient().getId()).isEqualTo(1);
    assertThat(result.getDiabetesAssessment()).isEqualTo(DiabetesAssessment.NONE);

  }

  @Test
  public void testBorderline() {
    DiabetesReport result = reportServiceUnderTest.createReport(2);

    assertThat(result.getPatient().getId()).isEqualTo(2);
    assertThat(result.getDiabetesAssessment()).isEqualTo(DiabetesAssessment.BORDERLINE);
  }

  @Test
  public void testInDanger() {
    DiabetesReport result = reportServiceUnderTest.createReport(3);

    assertThat(result.getPatient().getId()).isEqualTo(3);
    assertThat(result.getDiabetesAssessment()).isEqualTo(DiabetesAssessment.IN_DANGER);
  }

  @Test
  public void testEarlyOnSet() {
    DiabetesReport result = reportServiceUnderTest.createReport(4);

    assertThat(result.getPatient().getId()).isEqualTo(4);
    assertThat(result.getDiabetesAssessment()).isEqualTo(DiabetesAssessment.EARLY_ON_SET);
  }

}
