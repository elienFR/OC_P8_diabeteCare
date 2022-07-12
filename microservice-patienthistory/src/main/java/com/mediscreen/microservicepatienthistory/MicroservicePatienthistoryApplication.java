package com.mediscreen.microservicepatienthistory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.mediscreen.microservicepatienthistory")
public class MicroservicePatienthistoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicePatienthistoryApplication.class, args);
	}

}
