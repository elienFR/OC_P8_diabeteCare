package com.mediscreen.microservicereport;

import com.mediscreen.microservicereport.model.Gender;
import com.mediscreen.microservicereport.model.Patient;
import com.mediscreen.microservicereport.proxy.MicroservicePatientProxy;
import com.mediscreen.microservicereport.service.PatientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PatientServiceTest {
  @Autowired
  private PatientService patientServiceUnderTest;
  @MockBean
  private MicroservicePatientProxy microservicePatientProxyMocked;

  private Patient foundPatient;

  @BeforeEach
  public void init() {
    foundPatient = new Patient("lastname","firstname", Date.valueOf("1992-06-20"), Gender.M,null,null);
  }

  @Test
  public void getPatientTest() {
    Integer givenPatId = 1;
    foundPatient.setId(1);

    when(microservicePatientProxyMocked.findById(givenPatId))
      .thenReturn(foundPatient);

    Patient result = patientServiceUnderTest.getPatient(givenPatId);

    assertThat(result.getId()).isEqualTo(1);
  }

  @Test
  public void getAgeTest() {
    Integer expected = Period.between(foundPatient.getBirthdate().toLocalDate(), LocalDate.now()).getYears();

    Integer resultAge = patientServiceUnderTest.getAge(foundPatient);

    assertThat(resultAge).isEqualTo(expected);
  }

}
