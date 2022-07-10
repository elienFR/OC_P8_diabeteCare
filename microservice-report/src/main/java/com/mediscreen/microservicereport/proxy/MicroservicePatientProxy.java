package com.mediscreen.microservicereport.proxy;

import com.mediscreen.microservicereport.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "microservice-patient", url = "localhost:8090/patient")
public interface MicroservicePatientProxy {


  @GetMapping("/{id}")
  Patient findById(@PathVariable("id") Integer patId) ;

}
