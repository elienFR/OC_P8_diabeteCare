package com.mediscreen.microservicepatients.exceptions;


public class PatientNotFoundException extends RuntimeException {

  public PatientNotFoundException(String message) {
    super(message);
  }

}
