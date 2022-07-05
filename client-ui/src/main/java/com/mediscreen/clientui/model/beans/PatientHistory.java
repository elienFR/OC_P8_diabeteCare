package com.mediscreen.clientui.model.beans;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class PatientHistory {

  @Id
  private String id;

  //That is the patient id
  @NotBlank(message = "The patient's id must not be blank")
  private String patId;

  @NotBlank(message = "The patient's notes must not be blank")
  private String notes;

  private LocalDateTime localDateTime;

  public String getId() {
    return id;
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

  public LocalDateTime getLocalDateTime() {
    return localDateTime;
  }

  public void setLocalDateTime(LocalDateTime localDateTime) {
    this.localDateTime = localDateTime;
  }

  @Override
  public String toString() {
    return "PatientHistory{" +
      "patId='" + patId + '\'' +
      ", notes='" + notes + '\'' +
      '}';
  }
}
