package com.mediscreen.clientui.proxy;

import com.mediscreen.clientui.model.Gender;
import com.mediscreen.clientui.model.beans.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;

@FeignClient(name = "api-gateway", url = "localhost:8090/patient")
public interface MicroservicePatientsGatewayProxy {

  @GetMapping("/patient")
  PatientDTO getPatient(
    @NotBlank(message = "Family is mandatory !")
    @RequestParam("family") String lastname,
    @NotBlank(message = "Given is mandatory !")
    @RequestParam("given") String firstname,
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
    @RequestParam("dob") String birthdate);

  @PostMapping("/add")
  PatientDTO insert(
    @NotBlank(message = "Family is mandatory !")
    @RequestParam("family") String lastname,

    @NotBlank(message = "Given is mandatory !")
    @RequestParam("given") String firstname,

    @Pattern(regexp = "^(?:(\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]))|)$")
    @RequestParam("dob") String birthdate,

    @NotNull(message = "Gender is mandatory !")
    @RequestParam("sex") Gender gender,

    @RequestParam("address") String addressNumberAndStreet,

    // The pattern corresponds to US phone number with a number 'n' so it has to correspond to
    // 'nnn-nnn-nnnn' or the pattern has to correspond to an empty string.
    @RequestParam("phone")
    @Pattern(message = "must be a properly written US phone number, i.e : 123-456-7890",
      regexp = "^(?:(\\(?(\\d{3})\\)?[-.\\s]?(\\d{3})[-.\\s]?(\\d{4}))|)$") String phone);
}
