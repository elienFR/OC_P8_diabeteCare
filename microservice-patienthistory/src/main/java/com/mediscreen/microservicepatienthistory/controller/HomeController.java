package com.mediscreen.microservicepatienthistory.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api" + "/${api.ver}")
public class HomeController {

  private final static Logger LOGGER = LogManager.getLogger(HomeController.class);

  @Value("${api.ver}")
  String apiVer;

  @GetMapping("/")
  public String index(){
    LOGGER.info("GET : / --> Contacting index page...");
    return "Welcome to microservice-historypatient's API version : " + apiVer;
  }

}
