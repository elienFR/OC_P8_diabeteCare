package com.mediscreen.clientui.proxy;

import com.mediscreen.clientui.model.beans.PatientHistory;
import com.mediscreen.clientui.model.utils.layout.Paged;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "microservice-patienthistory", url = "${url.patientHistory.proxy}")
public interface MicroservicePatientsHistoryGatewayProxy {

  @GetMapping("/find")
  List<PatientHistory> find(@RequestParam String patId);

  @PostMapping("/add")
  PatientHistory add(@Valid @RequestBody PatientHistory patientHistory);

  @GetMapping("/findPage")
  Paged<PatientHistory> findByPatIdPaged(@RequestParam String patId,
                                         @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                         @RequestParam(required = false, defaultValue = "5") Integer pageSize);

}
