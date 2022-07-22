package com.mediscreen.microservicereport.service;

import com.mediscreen.microservicereport.model.*;
import com.mediscreen.microservicereport.service.utilities.FileUtilityService;
import com.mediscreen.microservicereport.service.utilities.ListUtilityService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReportService {

  private static final Logger LOGGER = LogManager.getLogger(ReportService.class);

  @Autowired
  private PatientService patientService;
  @Autowired
  private PatientHistoryService patientHistoryService;
  @Autowired
  private ListUtilityService listUtilityService;
  @Autowired
  private FileUtilityService fileUtilityService;

  // The file must be located in src/main/resources
  private static final String TRIGGER_WORDS_FILENAME = "triggerwords.txt";

  /**
   * This method creates a diabetes report.
   *
   * @param patId this is the patient's id to whom you want to create a report.
   * @return A diabetes report object.
   */
  public DiabetesReport createReport(Integer patId) {
    LOGGER.info("Getting patient's age and gender...");
    Patient patient = patientService.getPatient(patId);
    Integer patientAge = patientService.getAge(patient);
    LOGGER.info("Patient's age is : " + patientAge + ".");
    Gender patientGender = patient.getGender();
    LOGGER.info("Patient's gender is : " + patientGender.name() + ".");


    LOGGER.info("Finding trigger words in a list of patient's histories");
    Integer triggerWords = countDiabetesTrigger(patientHistoryService.findNotes(patId));

    //Creating reports
    DiabetesReport diabetesReport = new DiabetesReport();
    diabetesReport.setPatient(patient);

    if (Objects.isNull(triggerWords)) {
      LOGGER.error("An error occurred while creating diabetes assessment. triggerWords are null.");
      diabetesReport.setDiabetesAssessment(DiabetesAssessment.ERROR);
      return diabetesReport;
    } else {

      // Here is all the logic of report creation
      // -------- START OF REPORT'S CREATION LOGIC ----------
      // It could be optimised !
      if (triggerWords > 0) {
        LOGGER.info(triggerWords + " trigger words found.");
        LOGGER.info("Creating reports");

        if (!(triggerWords > 1)) {
          diabetesReport.setDiabetesAssessment(DiabetesAssessment.NONE);
        } else {
          if (!(triggerWords > 2)) {
            if (patientAge > 30) {
              diabetesReport.setDiabetesAssessment(DiabetesAssessment.BORDERLINE);
            } else {
              diabetesReport.setDiabetesAssessment(DiabetesAssessment.NONE);
            }
          } else {
            if (patientAge > 30) {
              if (!(triggerWords > 6)) {
                diabetesReport.setDiabetesAssessment(DiabetesAssessment.IN_DANGER);
              } else {
                if (!(triggerWords >= 8)) {
                  diabetesReport.setDiabetesAssessment(DiabetesAssessment.IN_DANGER);
                } else {
                  diabetesReport.setDiabetesAssessment(DiabetesAssessment.EARLY_ON_SET);
                }
              }
            } else {
              if (patientGender == Gender.M) {
                if (!(triggerWords > 3)) {
                  diabetesReport.setDiabetesAssessment(DiabetesAssessment.IN_DANGER);
                } else {
                  if (!(triggerWords > 4)) {
                    diabetesReport.setDiabetesAssessment(DiabetesAssessment.IN_DANGER);
                  } else {
                    diabetesReport.setDiabetesAssessment(DiabetesAssessment.EARLY_ON_SET);
                  }
                }
              } else {
                if (!(triggerWords > 4)) {
                  diabetesReport.setDiabetesAssessment(DiabetesAssessment.IN_DANGER);
                } else {
                  if (!(triggerWords >= 7)) {
                    diabetesReport.setDiabetesAssessment(DiabetesAssessment.IN_DANGER);
                  } else {
                    diabetesReport.setDiabetesAssessment(DiabetesAssessment.EARLY_ON_SET);
                  }
                }
              }
            }
          }
        }
      } else {
        LOGGER.info("No trigger words found !");
        diabetesReport.setDiabetesAssessment(DiabetesAssessment.NONE);
      }
    }
    // -------- END OF REPORT'S CREATION LOGIC ----------

    LOGGER.info("Diabetes report created !");
    LOGGER.info(diabetesReport);
    return diabetesReport;
  }

  /**
   * This method is used to count the number of trigger words of diabetes in a patient History
   *
   * @param patientsNotes is a list of patient histories
   * @return The number of trigger words found in the patient history (redundancy is not counted)
   */
  private Integer countDiabetesTrigger(List<PatientHistory> patientsNotes) {
    List<String> analysedList = patientsNotes.stream()
      .map(PatientHistory::getNotes).collect(Collectors.toList());
    try {
      return ListUtilityService
        .numberOfTriggerWords(
          analysedList.stream()
            //all strings in analysed list are put in lowercase and without accent
            .map(StringUtils::stripAccents)
            .map(String::toLowerCase)
            .collect(Collectors.toList()),
          FileUtilityService.convertLinesInList(TRIGGER_WORDS_FILENAME).stream()
            //all strings in trigger words' list are put in lowercase and without accent
            .map(StringUtils::stripAccents)
            .map(String::toLowerCase)
            .collect(Collectors.toList())
        )
        .keySet()
        .size();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * This method creates the way diabetes reports are to be displayed.
   *
   * @param patId is the patient id you want to create a report from.
   * @return a string displaying patient's report.
   */
  public String displayReport(Integer patId) {
    LOGGER.info("Creating report display for patID=" + patId + "...");
    DiabetesReport diabetesReport = createReport(patId);

    return "Patient: " +
      diabetesReport.getPatient().getFirstname() + " " +
      diabetesReport.getPatient().getLastname() + " " +
      "(age " + patientService.getAge(diabetesReport.getPatient()) + ") " +
      "diabetes assessment is:" + diabetesReport.getDiabetesAssessment().name();
  }
}
