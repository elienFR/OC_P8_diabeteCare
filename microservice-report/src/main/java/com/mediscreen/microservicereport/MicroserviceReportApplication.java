package com.mediscreen.microservicereport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.mediscreen.microservicereport")
public class MicroserviceReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceReportApplication.class, args);
	}

}
