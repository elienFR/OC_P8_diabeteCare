package com.mediscreen.microservicepatients.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Document(collection = "patients")
public class Patient {

  @Id
  private String id;
  @NotBlank(message="firstname is mandatory !")
  private String firstname;
  @NotBlank(message="lastname is mandatory !")
  private String lastname;
  @NotBlank(message="birthdate is mandatory !")
  private Date birthdate;
  @NotBlank(message="gender is mandatory !")
  private Gender gender;
  @NotBlank(message="address is mandatory !")
  private PostalAddress address;
  @NotBlank(message="phone number is mandatory !")
  private String phoneNumber;

  public Patient(){

  }
  public Patient(String firstname, String lastname, Date birthdate, Gender gender, PostalAddress address, String phoneNumber) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.birthdate = birthdate;
    this.gender = gender;
    this.address = address;
    this.phoneNumber = phoneNumber;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
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

  public PostalAddress getAddress() {
    return address;
  }

  public void setAddress(PostalAddress address) {
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
