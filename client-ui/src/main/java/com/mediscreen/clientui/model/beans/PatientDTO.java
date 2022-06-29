package com.mediscreen.clientui.model.beans;

import com.mediscreen.clientui.model.Gender;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.sql.Date;

public class PatientDTO {

  @NotBlank(message = "Family is mandatory !")
  private String family;
  @NotBlank(message = "Firstname is mandatory !")
  private String given;
  @PastOrPresent(message = "Date of birth must be past or present !")
  private Date dob;
  @NotNull(message = "Gender is mandatory !")
  private Gender gender;

  private String address;

  // The pattern corresponds to US phone number with a number 'n' so it has to correspond to
  // 'nnn-nnn-nnnn' or the pattern has to correspond to an empty string.
  @Pattern(message = "must be a properly written US phone number, i.e : 123-456-7890",
  regexp = "^(?:(\\(?(\\d{3})\\)?[-.\\s]?(\\d{3})[-.\\s]?(\\d{4}))|)$")
  private String phone;

  public PatientDTO(){

  }
  public PatientDTO(String family, String given, Date dob, Gender gender, String address, String phone) {
    this.family = family;
    this.given = given;
    this.dob = dob;
    this.gender = gender;
    this.address = address;
    this.phone = phone;
  }

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

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String toString() {
    return "PatientDTO{" +
      "family='" + family + '\'' +
      ", given='" + given + '\'' +
      ", dob='" + dob + '\'' +
      ", sex='" + gender + '\'' +
      ", address='" + address + '\'' +
      ", phone='" + phone + '\'' +
      '}';
  }
}
