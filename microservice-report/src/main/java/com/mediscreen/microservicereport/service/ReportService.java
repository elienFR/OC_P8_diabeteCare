package com.mediscreen.microservicereport.service;

import com.mediscreen.microservicereport.model.DiabetesReport;
import com.mediscreen.microservicereport.model.Patient;
import com.mediscreen.microservicereport.model.PatientHistory;
import com.mediscreen.microservicereport.service.utilities.FileUtilityService;
import com.mediscreen.microservicereport.service.utilities.ListUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ReportService {

  @Autowired
  private PatientService patientService;
  @Autowired
  private PatientHistoryService patientHistoryService;
  @Autowired
  private ListUtilityService listUtilityService;
  @Autowired
  private FileUtilityService fileUtilityService;

  /**
   * This method creates a diabetes report.
   *
   * @param patId this is the patient's id to whom you want to create a report.
   * @return A diabetes report object.
   */
  public DiabetesReport createReport(String patId) {
    List<PatientHistory> patientsNotes = patientHistoryService.getRecord(patId);
    Patient patient = patientService.getPatient(patId);
    Integer patientAge = patientService.getAge(patient);




    return null;
  }

  /**
   * This method is used to count the number of trigger words of diabetes in a patient History
   *
   * @param patientsNotes
   * @param triggerWordsList
   * @return The number of trigger words found in the patient history (redundancy is not counted)
   */
  private Integer countDiabetesTrigger(List<PatientHistory> patientsNotes,
                                       List<String> triggerWordsList) {

    List<String> analysedList = patientsNotes.stream()
      .map(PatientHistory::getNotes).collect(Collectors.toList());

    return ListUtilityService
      .numberOfTriggerWords(analysedList,triggerWordsList)
      .keySet()
      .size();
  }

}
