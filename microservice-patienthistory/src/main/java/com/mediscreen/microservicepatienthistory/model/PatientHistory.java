package com.mediscreen.microservicepatienthistory.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;

@Document(collection = "patientHistory")
@Transactional
public class PatientHistory {

  @Id
  private String id;

  //That is the patient id
  @Field(name = "patId")
  @NotBlank(message = "The patient's id must not be blank")
  private String patId;

  @Field(name = "notes")
  @NotBlank(message = "The patient's notes must not be blank")
  private String notes;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPatId() {
    return patId;
  }

  public void setPatId(String patId) {
    this.patId = patId;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  @Override
  public String toString() {
    return "PatientHistory{" +
      "patId='" + patId + '\'' +
      ", notes='" + notes + '\'' +
      '}';
  }
}
