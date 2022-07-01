package com.mediscreen.clientui.model.beans;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.sql.Date;

public class PatientDTOForSearch {

  @NotBlank(message = "Family is mandatory !")
  private String family;
  @NotBlank(message = "Firstname is mandatory !")
  private String given;
  @NotNull(message = "Date of birth is mandatory !")
  @PastOrPresent(message = "Date of birth must be past or present !")
  private Date dob;

  public String getFamily() {
    return family;
  }

  public void setFamily(String family) {
    this.family = family;
  }

  public String getGiven() {
    return given;
  }

  public void setGiven(String given) {
    this.given = given;
  }

  public Date getDob() {
    return dob;
  }

  public void setDob(Date dob) {
    this.dob = dob;
  }
}
