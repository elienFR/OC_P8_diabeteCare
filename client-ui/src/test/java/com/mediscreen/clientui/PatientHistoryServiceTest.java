package com.mediscreen.clientui;

import com.mediscreen.clientui.model.beans.PatientHistory;
import com.mediscreen.clientui.model.utils.layout.Paged;
import com.mediscreen.clientui.model.utils.layout.Paging;
import com.mediscreen.clientui.model.utils.layout.RestResponsePage;
import com.mediscreen.clientui.proxy.MicroservicePatientsHistoryGatewayProxy;
import com.mediscreen.clientui.service.PatientHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class PatientHistoryServiceTest {

  @Autowired
  private PatientHistoryService patientHistoryServiceUnderTest;

  @MockBean
  private MicroservicePatientsHistoryGatewayProxy patientsHistoryProxyMocked;

  @Test
  public void findByPatIdPageTest(){
    String givenPatId = "1";
    Integer givenPageNum = 1;
    Integer givenPageSize = 5;
    Paged<PatientHistory> expected = new Paged<>(new RestResponsePage<>(), Paging.of(28,1,5));

    when(patientsHistoryProxyMocked.findByPatIdPaged(givenPatId,givenPageNum,givenPageSize)).thenReturn(expected);

    Paged<PatientHistory> result = patientHistoryServiceUnderTest.findByPatIdPage(givenPatId,givenPageNum, givenPageSize);

    assertThat(result).isEqualTo(expected);
    verify(patientsHistoryProxyMocked,times(1)).findByPatIdPaged(givenPatId,givenPageNum,givenPageSize);
  }

  @Test
  public void insertTest() {
    PatientHistory givenPatientHistory = new PatientHistory();
    PatientHistory expected = new PatientHistory();

    when(patientsHistoryProxyMocked.add(givenPatientHistory)).thenReturn(expected);

    PatientHistory result = patientHistoryServiceUnderTest.insert(givenPatientHistory);

    assertThat(result).isEqualTo(expected);
    verify(patientsHistoryProxyMocked,times(1)).add(givenPatientHistory);
  }

}
