package com.mediscreen.microservicepatients.exceptions;

public class UnableToDeleteException extends RuntimeException {

  public UnableToDeleteException(){
    super("Unable to delete this user !");
  }

}
