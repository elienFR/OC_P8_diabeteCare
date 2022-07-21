package com.mediscreen.clientui.proxy;

import com.mediscreen.clientui.model.beans.DiabetesAssessment;
import com.mediscreen.clientui.model.beans.DiabetesReport;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

@FeignClient(name = "microservice-report", url = "localhost:8090/assess")
public interface MicroserviceReportGatewayProxy {


  @GetMapping("/assessOnly/{id}")
  DiabetesAssessment requestAssess(@NotBlank(message = "The patient id cannot be null")
                                   @PathVariable("id") String patId);

}
