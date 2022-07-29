package com.mediscreen.clientui;

import com.mediscreen.clientui.exceptions.AlreadyExistsException;
import com.mediscreen.clientui.exceptions.PatientNotFoundException;
import com.mediscreen.clientui.model.beans.Gender;
import com.mediscreen.clientui.model.beans.Patient;
import com.mediscreen.clientui.model.beans.PatientDTO;
import com.mediscreen.clientui.model.beans.PatientDTOForSearch;
import com.mediscreen.clientui.model.utils.layout.Paged;
import com.mediscreen.clientui.model.utils.layout.Paging;
import com.mediscreen.clientui.model.utils.layout.RestResponsePage;
import com.mediscreen.clientui.proxy.MicroservicePatientsGatewayProxy;
import com.mediscreen.clientui.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class PatientServiceTest {

  @Autowired
  private PatientService patientServiceUnderTest;
  @MockBean
  private MicroservicePatientsGatewayProxy patientsGatewayProxyMocked;

  String givenLastname;
  String givenFirstname;
  String givenBirthdate;

  @BeforeEach
  public void setUp() {
    givenLastname = "lastname";
    givenFirstname = "firstname";
    givenBirthdate = "1965-01-01";
  }

  @Test
  public void getPatientTest() {
    PatientDTO expected = new PatientDTO(givenLastname, givenFirstname, Date.valueOf(givenBirthdate), Gender.M, null, null);

    when(patientsGatewayProxyMocked.getPatient(givenLastname, givenFirstname, givenBirthdate)).thenReturn(expected);

    PatientDTO result = patientServiceUnderTest.getPatient(givenLastname, givenFirstname, givenBirthdate);

    assertThat(result).isEqualTo(expected);
    verify(patientsGatewayProxyMocked, times(1)).getPatient(givenLastname, givenFirstname, givenBirthdate);
  }

  @Test
  public void getPatientWithNotFoundExceptionTest() {
    when(patientsGatewayProxyMocked.getPatient(givenLastname, givenFirstname, givenBirthdate)).thenThrow(new PatientNotFoundException("Patient not found"));

    PatientNotFoundException pnfe = assertThrows(PatientNotFoundException.class, () -> patientServiceUnderTest.getPatient(givenLastname, givenFirstname, givenBirthdate));

    assertThat(pnfe.getMessage().contains("Patient not found")).isTrue();
    verify(patientsGatewayProxyMocked, times(1)).getPatient(givenLastname, givenFirstname, givenBirthdate);
  }

  @Test
  public void getPatientWithPatientDTOTest() {
    PatientDTOForSearch givenPatientDTOForSearch = new PatientDTOForSearch(givenLastname, givenFirstname, Date.valueOf(givenBirthdate));
    PatientDTO expected = new PatientDTO(givenLastname, givenFirstname, Date.valueOf(givenBirthdate), Gender.M, null, null);

    when(patientsGatewayProxyMocked.getPatient(givenLastname, givenFirstname, givenBirthdate)).thenReturn(expected);

    PatientDTO result = patientServiceUnderTest.getPatient(givenPatientDTOForSearch);

    assertThat(result).isEqualTo(expected);
    verify(patientsGatewayProxyMocked, times(1)).getPatient(givenLastname, givenFirstname, givenBirthdate);
  }

  @Test
  public void getPatientWithPatientDTOButNotFoundTest() {
    PatientDTOForSearch givenPatientDTOForSearch = new PatientDTOForSearch(givenLastname, givenFirstname, Date.valueOf(givenBirthdate));
    when(patientsGatewayProxyMocked.getPatient(givenLastname, givenFirstname, givenBirthdate)).thenThrow(new PatientNotFoundException("Patient not found"));

    PatientNotFoundException pnfe = assertThrows(PatientNotFoundException.class, () -> patientServiceUnderTest.getPatient(givenPatientDTOForSearch));

    assertThat(pnfe.getMessage().contains("Patient not found")).isTrue();
    verify(patientsGatewayProxyMocked, times(1)).getPatient(givenLastname, givenFirstname, givenBirthdate);
  }

  @Test
  public void saveTest() {
    PatientDTO givenPatientDTOToSave = new PatientDTO(givenLastname, givenFirstname, Date.valueOf(givenBirthdate), Gender.M, null, null);
    PatientDTO expected = new PatientDTO(givenLastname, givenFirstname, Date.valueOf(givenBirthdate), Gender.M, null, null);
    when(patientsGatewayProxyMocked.create(givenLastname, givenFirstname, givenBirthdate, Gender.M, null, null)).thenReturn(expected);

    PatientDTO result = patientServiceUnderTest.save(givenPatientDTOToSave);

    assertThat(result).isEqualTo(expected);
    verify(patientsGatewayProxyMocked, times(1)).create(givenLastname, givenFirstname, givenBirthdate, Gender.M, null, null);
  }

  @Test
  public void saveButAlreadyExistsTest() {
    PatientDTO givenPatientDTOToSave = new PatientDTO(givenLastname, givenFirstname, Date.valueOf(givenBirthdate), Gender.M, null, null);
    when(patientsGatewayProxyMocked.create(givenLastname, givenFirstname, givenBirthdate, Gender.M, null, null)).thenThrow(new AlreadyExistsException("patient already exists"));

    AlreadyExistsException aee = assertThrows(AlreadyExistsException.class, () -> patientServiceUnderTest.save(givenPatientDTOToSave));
    assertThat(aee.getMessage().contains("patient already exists")).isTrue();
    verify(patientsGatewayProxyMocked, times(1)).create(givenLastname, givenFirstname, givenBirthdate, Gender.M, null, null);
  }

  @Test
  public void getIdTest() {
    PatientDTO givenPatientDTO = new PatientDTO(givenLastname, givenFirstname, Date.valueOf(givenBirthdate), Gender.M, null, null);
    String expectedPatId = "1";

    when(patientsGatewayProxyMocked.getId(givenLastname, givenFirstname, givenBirthdate)).thenReturn(Integer.parseInt(expectedPatId));

    String result = patientServiceUnderTest.getId(givenPatientDTO);

    assertThat(result).isEqualTo(expectedPatId);
    verify(patientsGatewayProxyMocked, times(1)).getId(givenLastname, givenFirstname, givenBirthdate);
  }

  @Test
  public void getIdButNotFoundTest() {
    PatientDTO givenPatientDTO = new PatientDTO(givenLastname, givenFirstname, Date.valueOf(givenBirthdate), Gender.M, null, null);

    when(patientsGatewayProxyMocked.getId(givenLastname, givenFirstname, givenBirthdate)).thenThrow(new PatientNotFoundException("Patient not found"));

    PatientNotFoundException pnfe = assertThrows(PatientNotFoundException.class, () -> patientServiceUnderTest.getId(givenPatientDTO));

    assertThat(pnfe.getMessage().contains("Patient not found")).isTrue();
    verify(patientsGatewayProxyMocked, times(1)).getId(givenLastname, givenFirstname, givenBirthdate);
  }

  @Test
  public void getAllPatientPagedTest() {
    Integer givenPageNum = 1;
    Integer givenPageSize = 5;
    Paged<Patient> expectedPagedPatient = new Paged<>(new RestResponsePage<>(), Paging.of(28,1,5));

    when(patientsGatewayProxyMocked.findAllPaged(givenPageNum,givenPageSize)).thenReturn(expectedPagedPatient);

    Paged<Patient> result = patientServiceUnderTest.getAllPatientPaged(givenPageNum, givenPageSize);

    assertThat(result).isEqualTo(expectedPagedPatient);
    verify(patientsGatewayProxyMocked,times(1)).findAllPaged(givenPageNum,givenPageSize);
  }

}
