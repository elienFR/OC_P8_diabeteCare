package com.mediscreen.microservicepatienthistory.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "microservice-patient", url = "${url.patient.proxy}")
public interface MicroservicePatientProxy {

  @GetMapping("/id")
  Integer getIdFromFamily(@RequestParam("family") String lastname);

}
