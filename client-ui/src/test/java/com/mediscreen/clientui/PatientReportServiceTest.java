package com.mediscreen.clientui;


import com.mediscreen.clientui.model.beans.DiabetesAssessment;
import com.mediscreen.clientui.proxy.MicroserviceReportGatewayProxy;
import com.mediscreen.clientui.service.PatientReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ActiveProfiles("test")
@SpringBootTest
public class PatientReportServiceTest {

  @Autowired
  private PatientReportService patientReportServiceUnderTest;

  @MockBean
  private MicroserviceReportGatewayProxy reportGatewayProxyMocked;

  @Test
  public void getAssessTest() {
    Integer givenPatId = 1;
    DiabetesAssessment expected = DiabetesAssessment.NONE;

    when(reportGatewayProxyMocked.requestAssess(givenPatId.toString())).thenReturn(expected);

    DiabetesAssessment result = patientReportServiceUnderTest.getAssess(givenPatId);

    assertThat(result).isEqualTo(expected);
    verify(reportGatewayProxyMocked,times(1)).requestAssess(givenPatId.toString());
  }

}
