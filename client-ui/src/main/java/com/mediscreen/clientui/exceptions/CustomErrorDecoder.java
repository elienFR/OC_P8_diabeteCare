package com.mediscreen.clientui.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

  private final ErrorDecoder defaultErrorDecoder = new Default();

  @Override
  public Exception decode(String invoker, Response response) {
    // TODO : Should be more precise because when patient is not found (ie in reports, or during proxy calls) feign throws 404, but when proxy does not respond (which does not mean a not found patient) feigns also throws a 404, so throwing a PatientNotFoundException is not relevant
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
