package com.mediscreen.microservicereport.model;


public class DiabetesReport {

  // TODO : JSON FILTER to hide everything in patient except family and given

  Patient patient;
  DiabetesAssessment diabetesAssessment;


  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  public DiabetesAssessment getDiabetesAssessment() {
    return diabetesAssessment;
  }

  public void setDiabetesAssessment(DiabetesAssessment diabetesAssessment) {
    this.diabetesAssessment = diabetesAssessment;
  }
}
