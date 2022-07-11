package com.mediscreen.microservicereport.service.utilities;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileUtilityService {

  /**
   * This is a method that convert each line of a file into a string and add this string into a list.
   *
   * @param filePath is the path of the file relative to project folder.
   * @return a list of each string contained in file.
   */
  public static List<String> convertLinesInList(String filePath) throws IOException {
    // Used to know working directory
    // System.out.println("working directory : " + System.getProperty("user.dir"));
    try {
      FileReader fileReader = new FileReader(filePath);
      List<String> linesAsList = new ArrayList<>();
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line;

      while((line = bufferedReader.readLine()) != null) {
        linesAsList.add(line);
      }

      fileReader.close();
      return linesAsList;
    } catch (IOException e){
      e.printStackTrace();
      throw e;
    }
  }



}
