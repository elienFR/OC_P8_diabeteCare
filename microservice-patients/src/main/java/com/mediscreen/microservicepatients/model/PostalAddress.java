package com.mediscreen.microservicepatients.model;

public class PostalAddress {

  private String street;
  private String city;
  private String zipCode;
  private String state;
  private String country;

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @Override
  public String toString() {
    return "PostalAddress{" +
      "street='" + street + '\'' +
      ", city='" + city + '\'' +
      ", zipCode='" + zipCode + '\'' +
      ", state='" + state + '\'' +
      ", country='" + country + '\'' +
      '}';
  }
}
