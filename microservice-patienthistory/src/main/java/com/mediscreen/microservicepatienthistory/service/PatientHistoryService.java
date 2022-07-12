package com.mediscreen.microservicepatienthistory.service;

import com.mediscreen.microservicepatienthistory.model.PatientHistory;
import com.mediscreen.microservicepatienthistory.model.utils.layout.Paged;
import com.mediscreen.microservicepatienthistory.model.utils.layout.Paging;
import com.mediscreen.microservicepatienthistory.model.utils.layout.RestResponsePage;
import com.mediscreen.microservicepatienthistory.repository.PatientHistoryRepository;
import com.mediscreen.microservicepatienthistory.utils.CsvImporterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PatientHistoryService {
  private static final boolean IMPORT_ACTIVE = false;
  public static final boolean IN_TEST = true;
  private static final String FILEPATH = "resources/Notes_du_praticien_P9.csv";

  private final static Logger LOGGER = LogManager.getLogger(PatientHistoryService.class);

  @Autowired
  private PatientHistoryRepository patientHistoryRepository;
  @Autowired
  private PatientService patientService;

  public PatientHistory insert(PatientHistory patientHistory) {
    LOGGER.info("Inserting " + patientHistory + " into database.");
    patientHistory.setLocalDateTime(LocalDateTime.now());
    return patientHistoryRepository.insert(patientHistory);
  }

  public List<PatientHistory> findByPatId(String patId) {
    LOGGER.info("Retrieving patient's history with patId=" + patId);
    return patientHistoryRepository.findByPatId(patId);
  }

  public Paged<PatientHistory> findByPatIdPaged(int pageNumber, int pageSize, String patId) {
    LOGGER.info("Gathering paged PatientHistory from patID=" + patId + ". Selected page " +
      "is " + pageNumber + " and num of elements per page is " + pageSize + ".");

    PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "datetime"));

    Page<PatientHistory> patientHistoryPage = patientHistoryRepository.findByPatId(patId, pageRequest);

    RestResponsePage<PatientHistory> restResponsePage =
      new RestResponsePage<>(patientHistoryPage.getContent(), patientHistoryPage.getPageable(), patientHistoryPage.getTotalElements());

    return new Paged<>(
      restResponsePage,
      Paging.of(patientHistoryPage.getTotalPages(),
        pageNumber, pageSize)
    );

  }

  /**
   * This method is NOT TO BE USED.
   * It is only here to simplify the importation of a benchmark file of patient histories
   * Final variables must be modified in code to use this method.
   */
  public void insertCsv() {
    try {
      List<String[]> listToImport = CsvImporterService.parseCSV(FILEPATH);
      if (IMPORT_ACTIVE) {
        listToImport.forEach(
          aoS -> {
            PatientHistory patientHistoryToImport = new PatientHistory();
            Integer patId = patientService.getIdFromLastName(aoS[0]);
            patientHistoryToImport.setPatId(patId.toString());
            patientHistoryToImport.setNotes(aoS[1]);
            if (!IN_TEST) {
              insert(patientHistoryToImport);
            }
          }
        );
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
