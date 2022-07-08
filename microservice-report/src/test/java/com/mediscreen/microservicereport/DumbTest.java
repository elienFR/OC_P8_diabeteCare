package com.mediscreen.microservicereport;


import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DumbTest {

  @Test
  public void dumbTest() {
    String testedString = "a New SENTENCE with a specific word.";
    System.out.println(testedString.toLowerCase());
    assertThat(testedString.toLowerCase().contains("new")).isTrue();
  }

  @Test
  public void noAccentTest() {
    String testedString = "C'est pas beau ça une phrase à peur près sans accents ?";
    System.out.println(StringUtils.stripAccents(testedString));
  }

  @Test
  public void parseFileTriggerWordsTest() {
    try {
      FileReader fileReader = new FileReader("resources/triggerwords.txt");

      List<String> lineToList = new ArrayList<>();

      BufferedReader bufferedReader = new BufferedReader(fileReader);

      StringBuffer stringBuffer = new StringBuffer();
      String line;

      while ((line = bufferedReader.readLine()) != null) {
        stringBuffer.append(line);
        lineToList.add(StringUtils.stripAccents(line).toLowerCase());
        stringBuffer.append("\n");
      }

      fileReader.close();
      System.out.println("Contents of file :");
      System.out.println(stringBuffer);
      System.out.println("Contents of list :");
      lineToList.forEach(System.out::println);
    } catch (IOException e) {
      e.printStackTrace();

    } finally {



    }


  }

}
