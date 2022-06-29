package com.mediscreen.clientui.proxy;

import com.mediscreen.clientui.model.beans.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@FeignClient(name="api-gateway", url="localhost:8090/patient")
public interface MicroservicePatientsGatewayProxy {

  @GetMapping("/patient")
  PatientDTO getPatient(
    @NotBlank(message = "Family is mandatory !")
    @RequestParam("family") String lastname,
    @NotBlank(message = "Given is mandatory !")
    @RequestParam("given") String firstname,
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
    @RequestParam("dob") String birthdate);

}
