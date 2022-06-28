package com.mediscreen.microservicepatients.controller.exceptions;

public class AlreadyExistException extends RuntimeException {
  public AlreadyExistException(String message) {
    super(message);
  }
}
