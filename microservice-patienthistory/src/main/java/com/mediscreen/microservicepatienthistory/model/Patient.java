package com.mediscreen.microservicepatienthistory.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.sql.Date;


public class Patient {

  @NotBlank(message = "id cannot be blank")
  private Integer id;

  @NotBlank(message="firstname is mandatory !")
  private String firstname;


  @NotBlank(message="lastname is mandatory !")
  private String lastname;

  @PastOrPresent(message = "Date of birth must be past or present !")
  private Date birthdate;

  @NotNull(message="gender is mandatory !")
  private Gender gender;

  private String address;

  // The pattern corresponds to US phone number with a number 'n' so it has to correspond to
  // 'nnn-nnn-nnnn' or the pattern has to correspond to an empty string.
  @Pattern(message = "must be a properly written US phone number, i.e : 123-456-7890",
    regexp = "^(?:(\\(?(\\d{3})\\)?[-.\\s]?(\\d{3})[-.\\s]?(\\d{4}))|)$")
  private String phoneNumber;

  public Patient(){

  }
  public Patient(String firstname, String lastname, Date birthdate, Gender gender, String address, String phoneNumber) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.birthdate = birthdate;
    this.gender = gender;
    this.address = address;
    this.phoneNumber = phoneNumber;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public Date getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(Date birthdate) {
    this.birthdate = birthdate;
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

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public String toString() {
    return "Patient{" +
      "id='" + id + '\'' +
      ", firstname='" + firstname + '\'' +
      ", lastname='" + lastname + '\'' +
      ", birthdate=" + birthdate +
      ", gender=" + gender +
      ", address=" + address +
      ", phoneNumber='" + phoneNumber + '\'' +
      '}';
  }
}
