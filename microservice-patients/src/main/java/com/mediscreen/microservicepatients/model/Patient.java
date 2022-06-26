package com.mediscreen.microservicepatients.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "patients")
public class Patient {

  @Id
  private String id;
  private String firstname;
  private String lastname;
  private Date birthdate;
  private Gender gender;
  private PostalAddress address;
  private String phoneNumber;



}
