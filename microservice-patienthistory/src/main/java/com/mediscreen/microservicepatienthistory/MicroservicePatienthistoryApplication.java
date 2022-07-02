package com.mediscreen.microservicepatienthistory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroservicePatienthistoryApplication {

	public static void main(String[] args) {
		// TODO : Externalize config
		SpringApplication.run(MicroservicePatienthistoryApplication.class, args);
	}

}
