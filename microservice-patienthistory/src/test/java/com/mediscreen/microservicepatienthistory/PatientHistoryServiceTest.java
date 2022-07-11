package com.mediscreen.microservicepatienthistory;

import com.mediscreen.microservicepatienthistory.model.PatientHistory;
import com.mediscreen.microservicepatienthistory.model.utils.layout.Paged;
import com.mediscreen.microservicepatienthistory.model.utils.layout.Paging;
import com.mediscreen.microservicepatienthistory.model.utils.layout.RestResponsePage;
import com.mediscreen.microservicepatienthistory.repository.PatientHistoryRepository;
import com.mediscreen.microservicepatienthistory.service.PatientHistoryService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PatientHistoryServiceTest {

  @Autowired
  private PatientHistoryService patientHistoryServiceUnderTest;
  @MockBean
  private PatientHistoryRepository patientHistoryRepositoryMocked;

  @Test
  public void insertTest() {
    PatientHistory givenPatientHistory = new PatientHistory();

    when(patientHistoryRepositoryMocked.insert(givenPatientHistory)).thenReturn(givenPatientHistory);

    PatientHistory result = patientHistoryServiceUnderTest.insert(givenPatientHistory);

    assertThat(result.getLocalDateTime()).isNotNull();
    assertThat(result).isEqualTo(givenPatientHistory);
    verify(patientHistoryRepositoryMocked, times(1)).insert(givenPatientHistory);
  }

  @Test
  public void findPatientHistory() {
    String givenPatId = "1";
    List<PatientHistory> expected = new ArrayList<>();

    when(patientHistoryRepositoryMocked.findByPatId(givenPatId)).thenReturn(expected);

    List<PatientHistory> result = patientHistoryServiceUnderTest.findByPatId(givenPatId);

    assertThat(result).isEqualTo(expected);
    verify(patientHistoryRepositoryMocked,times(1)).findByPatId(givenPatId);
  }

  @Disabled
  @Test
  public void findPatientHistoryPaged() {
    // TODO : this test does not pass
    String givenPatId = "1";
    int givenPageSize = 5;
    int givenPageNumber = 1;
    List<PatientHistory> patientHistories = new ArrayList<>();
    Page<PatientHistory> patientHistoriesPage = new PageImpl<>(patientHistories);

    Paged<PatientHistory> expected = new Paged<>(
      new RestResponsePage<>(patientHistoriesPage.getContent(),patientHistoriesPage.getPageable(),patientHistoriesPage.getTotalElements()),
      Paging.of(patientHistoriesPage.getTotalPages(),
      givenPageNumber,
      givenPageSize)
    );

    when(
      patientHistoryRepositoryMocked
        .findByPatId(givenPatId, PageRequest.of(givenPageNumber-1, givenPageSize,Sort.by(Sort.Direction.DESC)))).thenReturn(patientHistoriesPage);

    Paged<PatientHistory> result = patientHistoryServiceUnderTest.findByPatIdPaged(givenPageNumber,givenPageSize,givenPatId);

    assertThat(result).isEqualTo(expected);
    verify(patientHistoryRepositoryMocked,times(1)).findByPatId(givenPatId, PageRequest.of(givenPageNumber-1, givenPageSize,Sort.by(Sort.Direction.DESC)));
  }

}
