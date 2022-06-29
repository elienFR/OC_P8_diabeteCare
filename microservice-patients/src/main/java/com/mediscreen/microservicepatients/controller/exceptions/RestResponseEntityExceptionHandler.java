package com.mediscreen.microservicepatients.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ValidationException.class)
  protected ResponseEntity<Object> validationException() {
    return ResponseEntity.badRequest().body("One of the arguments is not correct !");
  }

  @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
  protected ResponseEntity<Object> alreadyExistException() {
    return ResponseEntity.status(HttpStatus.CONFLICT).body("This patient already exists !");
  }

}
