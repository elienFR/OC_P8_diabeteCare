package com.mediscreen.microservicepatienthistory;

import com.mediscreen.microservicepatienthistory.proxy.MicroservicePatientProxy;
import com.mediscreen.microservicepatienthistory.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class PatientServiceTest {

  @Autowired
  private PatientService patientServiceUnderTest;
  @MockBean
  private MicroservicePatientProxy microservicePatientProxyMocked;

  @Test
  public void getIdFromLastNameTest() {
    String givenLastName = "lastname";
    Integer expected = 1;

    when(microservicePatientProxyMocked.getIdFromFamily(givenLastName)).thenReturn(expected);

    Integer result = patientServiceUnderTest.getIdFromLastName(givenLastName);

    assertThat(result).isEqualTo(expected);
    verify(microservicePatientProxyMocked,times(1)).getIdFromFamily(givenLastName);
  }

}
