package com.mediscreen.microservicereport;

import com.mediscreen.microservicereport.service.utilities.FileUtilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FileUtilityServiceTest {

  @Autowired
  private FileUtilityService fileUtilityService;

  String givenFile = new String();

  @BeforeEach
  public void init(){
    givenFile = "resources/testfile.txt";
  }


  @Test
  public void convertLinesInListTest() {

    List<String> results = fileUtilityService.convertLinesInList(givenFile);

    assertThat(results.contains("Line1")).isTrue();
    assertThat(results.contains("this is line 2"));
  }


}
