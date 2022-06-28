package com.mediscreen.microservicepatients.controller.exceptions;


public class PatientNotFoundException extends RuntimeException {

  public PatientNotFoundException(String message) {
    super(message);
  }

}
