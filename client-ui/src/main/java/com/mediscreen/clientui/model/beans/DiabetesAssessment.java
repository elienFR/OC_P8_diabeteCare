package com.mediscreen.clientui.model.beans;

public enum DiabetesAssessment {

  NONE("None"),
  BORDERLINE("Borderline"),
  IN_DANGER("In danger"),
  EARLY_ON_SET("Early on set"),
  ERROR("Error in creating assessment");

  final String name;

  DiabetesAssessment(String name) {
    this.name=name;
  }

  @Override
  public String toString() {
    return "Assessment : "
      + name;
  }
}
