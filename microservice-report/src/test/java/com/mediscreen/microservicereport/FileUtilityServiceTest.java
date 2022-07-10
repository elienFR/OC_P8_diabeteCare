package com.mediscreen.microservicereport;

import com.mediscreen.microservicereport.service.utilities.FileUtilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FileUtilityServiceTest {

  @Autowired
  private FileUtilityService fileUtilityService;

  String givenFile;

  @BeforeEach
  public void init(){
    givenFile = "resources/testfile.txt";
  }


  @Test
  public void convertLinesInListTest() {
    List<String> results = new ArrayList<>();
    try {
      results = FileUtilityService.convertLinesInList(givenFile);
    } catch(IOException e){
      e.printStackTrace();
    }
    assertThat(results.contains("Line1")).isTrue();
    assertThat(results.contains("this is line 2")).isTrue();
  }

  @Test
  public void convertLinesInListTestException() {

    assertThrows(IOException.class, () -> FileUtilityService.convertLinesInList("somepath"));

  }


}
