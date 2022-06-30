package com.mediscreen.clientui.configurations;

import com.mediscreen.clientui.exceptions.CustomErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignExceptionConfig {

  @Bean
  public CustomErrorDecoder mCustomErrorDecoder(){
    return new CustomErrorDecoder();
  }

}
