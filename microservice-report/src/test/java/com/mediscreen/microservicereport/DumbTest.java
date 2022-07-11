package com.mediscreen.microservicereport;


import com.mediscreen.microservicereport.service.utilities.FileUtilityService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DumbTest {

  @Test
  public void dumbTest() {
    Path currentDir = Paths.get(".");
    System.out.println(currentDir.toAbsolutePath());
  }

  @Test
  public void anotherDumbTest() {
    System.out.println("working directory : " + System.getProperty("user.dir"));
  }



}
