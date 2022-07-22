package com.mediscreen.microservicereport.service.utilities;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FileUtilityService {

  /**
   * This is a method that convert each line of a file into a string and add this string into a list.
   *
   * @param filename is the name of the path located in src/main/resources folder.
   * @return a list of each string contained in file.
   */
  public static List<String> convertLinesInList(String filename) throws IOException {
    // Used to know working directory
    // System.out.println("working directory : " + System.getProperty("user.dir"));
    InputStreamReader streamReader = null;
    BufferedReader bufferedReader = null;

    try {
      streamReader = new InputStreamReader(
        FileUtilityService.getFileFromResourceAsStream(filename),
        StandardCharsets.UTF_8
      );
      List<String> linesAsList = new ArrayList<>();
      bufferedReader = new BufferedReader(streamReader);
      String line;

      while ((line = bufferedReader.readLine()) != null) {
        linesAsList.add(line);
      }

      return linesAsList;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (!Objects.isNull(bufferedReader)) {
        bufferedReader.close();
      }
      if (!Objects.isNull(streamReader)) {
        streamReader.close();
      }
    }
  }

  /**
   * Get a file from the resources' folder works everywhere, IDEA, unit test and JAR file.
   *
   * @param filename is the name of the file.
   * @return an InputStream for the designated file.
   */
  private static InputStream getFileFromResourceAsStream(String filename) throws FileNotFoundException {

    // The class loader that loaded the class
    ClassLoader classLoader = FileUtilityService.class.getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(filename);

    // The stream holding the file content
    if (Objects.isNull(inputStream)) {
      throw new FileNotFoundException("file not found ! " + filename);
    } else {
      return inputStream;
    }
  }


}
