package com.mediscreen.clientui;

import com.mediscreen.clientui.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.mediscreen.clientui")
@EnableDiscoveryClient
public class ClientUiApplication {

  @Autowired
  private PatientService patientService;

  public static void main(String[] args) {
    SpringApplication.run(ClientUiApplication.class, args);
  }

}
