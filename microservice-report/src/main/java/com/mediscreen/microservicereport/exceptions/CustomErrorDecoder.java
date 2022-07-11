package com.mediscreen.microservicereport.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

  private final ErrorDecoder defaultErrorDecoder = new Default();

  @Override
  public Exception decode(String invoker, Response response) {
    if (response.status() == 404) {
      return new PatientNotFoundException(
        "Patient not found."
      );
    } else if (response.status() == 409) {
      return new AlreadyExistsException(
        "Patient already exists."
      );
    }
    return defaultErrorDecoder.decode(invoker, response);
  }
}
