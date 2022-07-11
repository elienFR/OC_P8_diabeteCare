package com.mediscreen.microservicereport.configurations;


import com.mediscreen.microservicereport.exceptions.CustomErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignExceptionConfig {

  @Bean
  public CustomErrorDecoder mCustomErrorDecoder(){
    return new CustomErrorDecoder();
  }

}
