package com.mediscreen.microservicereport.proxy;

import com.mediscreen.microservicereport.model.PatientHistory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservice-patienthistory", url = "${url.patientHistory.proxy}")
public interface MicroservicePatientHistoryProxy {

  @GetMapping("/find")
  List<PatientHistory> find(@RequestParam String patId);

}
