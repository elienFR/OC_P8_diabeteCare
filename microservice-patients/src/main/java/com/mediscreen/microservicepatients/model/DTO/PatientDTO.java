package com.mediscreen.microservicepatients.model.DTO;

import com.mediscreen.microservicepatients.model.Gender;

import java.util.Date;

public class PatientDTO {

  private String family;
  private String given;
  private Date dob;
  private Gender gender;
  private String address;
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
