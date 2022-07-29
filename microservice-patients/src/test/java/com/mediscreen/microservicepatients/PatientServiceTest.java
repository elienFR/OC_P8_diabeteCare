package com.mediscreen.microservicepatients;

import com.mediscreen.microservicepatients.exceptions.AlreadyExistsException;
import com.mediscreen.microservicepatients.exceptions.PatientNotFoundException;
import com.mediscreen.microservicepatients.model.DTO.PatientDTO;
import com.mediscreen.microservicepatients.model.Gender;
import com.mediscreen.microservicepatients.model.Patient;
import com.mediscreen.microservicepatients.model.layout.Paged;
import com.mediscreen.microservicepatients.model.layout.Paging;
import com.mediscreen.microservicepatients.model.layout.RestResponsePage;
import com.mediscreen.microservicepatients.repository.PatientRepository;
import com.mediscreen.microservicepatients.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class PatientServiceTest {

  @Autowired
  private PatientService patientServiceUnderTest;
  @MockBean
  private PatientRepository patientRepositoryMocked;

  private Patient patient1;
  private Patient patient2;
  private Patient patient3;
  private PatientDTO patientDTO1;
  private PatientDTO patientDTO2;
  private PatientDTO patientDTO3;

  @BeforeEach
  public void setUp() {
    patient1 = new Patient("firstname1", "lastname1", Date.valueOf("1965-01-01"), Gender.M, null, null);
    patient2 = new Patient("firstname2", "lastname2", Date.valueOf("1965-01-02"), Gender.F, null, null);
    patient3 = new Patient("firstname3", "lastname3", Date.valueOf("1965-01-03"), Gender.M, null, null);
    patientDTO1 = new PatientDTO("lastname1", "firstname1", Date.valueOf("1965-01-01"), Gender.M, null, null);
    patientDTO2 = new PatientDTO("lastname2", "firstname2", Date.valueOf("1965-01-01"), Gender.M, null, null);
    patientDTO3 = new PatientDTO("lastname3", "firstname3", Date.valueOf("1965-01-01"), Gender.M, null, null);

  }

  @Test
  public void getAllTest() {
    Iterable<Patient> givenPatients = List.of(patient1, patient2, patient3);
    when(patientRepositoryMocked.findAll()).thenReturn(givenPatients);
    List<PatientDTO> result = patientServiceUnderTest.getAll();

    assertThat(result.get(0).getFamily()).isEqualTo("lastname1");
    assertThat(result.get(1).getFamily()).isEqualTo("lastname2");
    assertThat(result.get(2).getFamily()).isEqualTo("lastname3");
  }

  @Test
  public void createTest() {
    String givenLastname = "lastname1";
    String givenFirstname = "firstname1";
    String givenBirthdate = "1965-01-01";
    Gender givenGender = Gender.M;
    PatientDTO expected = patientDTO1;

    when(patientRepositoryMocked.save(any(Patient.class))).thenReturn(patient1);

    PatientDTO result = patientServiceUnderTest.create(givenLastname, givenFirstname, givenBirthdate, givenGender, null, null);

    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void createButAlreadyExistsTest() {
    String givenLastname = "lastname1";
    String givenFirstname = "firstname1";
    String givenBirthdate = "1965-01-01";
    Gender givenGender = Gender.M;
    PatientDTO expected = patientDTO1;

    when(patientRepositoryMocked.save(any(Patient.class))).thenThrow(new AlreadyExistsException("Patient Already exists"));

    AlreadyExistsException aee = assertThrows(AlreadyExistsException.class, () -> patientServiceUnderTest.create(givenLastname, givenFirstname, givenBirthdate, givenGender, null, null));

    assertThat(aee.getMessage().contains("Patient Already exists")).isTrue();
  }

  @Test
  public void findPatientDTOByLastnameAndFirstnameAndBirthdateTest() {
    String givenLastname = "lastname1";
    String givenFirstname = "firstname1";
    String givenBirthdate = "1965-01-01";
    when(patientRepositoryMocked
      .findByLastnameAndFirstnameAndBirthdate(
        givenLastname, givenFirstname, Date.valueOf(givenBirthdate))
    ).thenReturn(Optional.of(patient1));

    Optional<PatientDTO> expected = Optional.of(patientDTO1);

    Optional<PatientDTO> result = patientServiceUnderTest
      .findPatientDTOByLastnameAndFirstnameAndBirthdate(
        givenLastname,
        givenFirstname,
        givenBirthdate
      );

    assertThat(result.get()).isEqualTo(expected.get());
  }

  @Test
  public void deleteSuccessTest() {
    String givenLastname = "lastname1";
    String givenFirstname = "firstname1";
    String givenBirthdate = "1965-01-01";

    when(patientRepositoryMocked
      .findByLastnameAndFirstnameAndBirthdate(
        givenLastname, givenFirstname, Date.valueOf(givenBirthdate))
    ).thenReturn(Optional.of(patient1));

    boolean result = patientServiceUnderTest.delete(givenLastname, givenFirstname, givenBirthdate);

    assertThat(result).isTrue();
  }

  @Test
  public void deleteNotFoundTest() {
    String givenLastname = "lastname1";
    String givenFirstname = "firstname1";
    String givenBirthdate = "1965-01-01";

    when(patientRepositoryMocked
      .findByLastnameAndFirstnameAndBirthdate(
        givenLastname, givenFirstname, Date.valueOf(givenBirthdate))
    ).thenReturn(Optional.empty());

    boolean result = patientServiceUnderTest.delete(givenLastname, givenFirstname, givenBirthdate);

    assertThat(result).isFalse();
  }

  @Test
  public void updateTest() {
    String givenLastname = "lastname1";
    String givenFirstname = "firstname1";
    String givenBirthdate = "1965-01-01";
    Gender givenUpdatedGender = Gender.F;
    String givenUpdatedAddress = "somme new address";
    String givenUpdatedPhone = "some new phone";

    when(patientRepositoryMocked
      .findByLastnameAndFirstnameAndBirthdate(
        givenLastname, givenFirstname, Date.valueOf(givenBirthdate))
    ).thenReturn(Optional.of(patient1));

    Patient expected = patient1;
    expected.setGender(givenUpdatedGender);
    expected.setAddress(givenUpdatedAddress);
    expected.setPhoneNumber(givenUpdatedPhone);

    PatientDTO expectedDTO = patientDTO1;
    expectedDTO.setGender(givenUpdatedGender);
    expectedDTO.setAddress(givenUpdatedAddress);
    expectedDTO.setPhone(givenUpdatedPhone);

    when(patientRepositoryMocked.save(any(Patient.class))).thenReturn(expected);

    PatientDTO result = patientServiceUnderTest.update(
      givenLastname,
      givenFirstname,
      givenBirthdate,
      givenUpdatedGender,
      givenUpdatedAddress,
      givenUpdatedPhone
    );

    assertThat(result).isEqualTo(expectedDTO);
  }

  @Test
  public void updateButNotFoundTest() {
    String givenLastname = "lastname1";
    String givenFirstname = "firstname1";
    String givenBirthdate = "1965-01-01";
    Gender givenUpdatedGender = Gender.F;
    String givenUpdatedAddress = "somme new address";
    String givenUpdatedPhone = "some new phone";

    when(patientRepositoryMocked
      .findByLastnameAndFirstnameAndBirthdate(
        givenLastname, givenFirstname, Date.valueOf(givenBirthdate))
    ).thenReturn(Optional.empty());

    PatientNotFoundException pnfe = assertThrows(PatientNotFoundException.class,
      () -> patientServiceUnderTest.update(givenLastname,
        givenFirstname,
        givenBirthdate,
        givenUpdatedGender,
        givenUpdatedAddress,
        givenUpdatedPhone)
    );

    assertThat(pnfe.getMessage().contains("The patient you try to update has not been found !")).isTrue();
  }

  @Test
  public void getIdTest() {
    String givenLastname = "lastname1";
    String givenFirstname = "firstname1";
    String givenBirthdate = "1965-01-01";
    Integer givenId = 1;
    patient1.setId(givenId);

    when(patientRepositoryMocked
      .findByLastnameAndFirstnameAndBirthdate(
        givenLastname, givenFirstname, Date.valueOf(givenBirthdate))
    ).thenReturn(Optional.of(patient1));

    Integer result = patientServiceUnderTest.getId(givenLastname, givenFirstname, givenBirthdate);

    assertThat(result).isEqualTo(givenId);
  }

  @Test
  public void getIdButNotFoundTest() {
    String givenLastname = "lastname1";
    String givenFirstname = "firstname1";
    String givenBirthdate = "1965-01-01";
    Integer givenId = 1;
    patient1.setId(givenId);

    when(patientRepositoryMocked
      .findByLastnameAndFirstnameAndBirthdate(
        givenLastname, givenFirstname, Date.valueOf(givenBirthdate))
    ).thenReturn(Optional.empty());

    Integer result = patientServiceUnderTest.getId(givenLastname, givenFirstname, givenBirthdate);

    assertThat(result).isNull();
  }

  @Test
  public void findByIdTest() {
    Integer givenId = 1;
    when(patientRepositoryMocked.findById(givenId)).thenReturn(Optional.of(patient1));

    Patient result = patientServiceUnderTest.findById(givenId);

    assertThat(result).isEqualTo(patient1);
  }

  @Test
  public void findByIdButNotFoundTest() {
    Integer givenId = 1;
    when(patientRepositoryMocked.findById(givenId)).thenReturn(Optional.empty());

    assertThrows(PatientNotFoundException.class, () -> patientServiceUnderTest.findById(givenId));
  }

  @Test
  public void getIdFromFamilyTest() {
    String givenLastName = "lastname1";
    Integer expectedId = 1;
    patient1.setId(expectedId);

    when(patientRepositoryMocked.findByLastname(givenLastName)).thenReturn(Optional.of(patient1));

    Integer result = patientServiceUnderTest.getIdFromFamily(givenLastName);

    assertThat(result).isEqualTo(expectedId);
  }

  @Test
  public void getIdFromFamilyButNotFoundTest() {
    String givenLastName = "lastname1";

    when(patientRepositoryMocked.findByLastname(givenLastName)).thenReturn(Optional.empty());

    assertThrows(PatientNotFoundException.class,()->patientServiceUnderTest.getIdFromFamily(givenLastName));
  }

  @Test
  public void findPatientHistoryPaged() {
    String givenPatId = "1";
    int givenPageSize = 5;
    int givenPageNumber = 1;
    List<Patient> patients = List.of(patient1,patient2,patient3);
    Page<Patient> patientHistoriesPage = new PageImpl<>(patients);

    Paged<Patient> expected = new Paged<>(
      new RestResponsePage<>(patientHistoriesPage.getContent(),patientHistoriesPage.getPageable(),patientHistoriesPage.getTotalElements()),
      Paging.of(patientHistoriesPage.getTotalPages(),
        givenPageNumber,
        givenPageSize)
    );

    when(
      patientRepositoryMocked
        .findAll(PageRequest.of(givenPageNumber-1, givenPageSize, Sort.by(Sort.Direction.ASC,"lastname")))).thenReturn(patientHistoriesPage);

    Paged<Patient> result = patientServiceUnderTest.findAllPaged(givenPageNumber,givenPageSize);

    assertThat(result.getPage().getContent()).isEqualTo(expected.getPage().getContent());
  }

}
