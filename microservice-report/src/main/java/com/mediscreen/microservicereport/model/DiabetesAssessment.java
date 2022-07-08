package com.mediscreen.microservicereport.model;

public enum DiabetesAssessment {

  NONE("None"),
  BORDERLINE("Borderline"),
  IN_DANGER("In danger"),
  EARLY_ON_SET("Early on set");

  String name;

  DiabetesAssessment(String name) {
    this.name=name;
  }

  @Override
  public String toString() {
    return name;
  }
}
