package com.mediscreen.microservicepatienthistory.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "patientHistory")
public class PatientHistory {

  @Id
  private String id;
  private Patient patient;
  private String history;
}
