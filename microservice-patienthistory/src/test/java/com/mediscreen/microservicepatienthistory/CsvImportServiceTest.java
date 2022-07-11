package com.mediscreen.microservicepatienthistory;

import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@SpringBootTest
public class CsvImportServiceTest {

  @Test
  public void testCsvReader() throws Exception {

    try{
      Path path = Paths.get(ClassLoader.getSystemResource("resources/Notes_du_praticien_P9.csv").toURI());
      Reader reader = Files.newBufferedReader(path);


      CSVReader csvReader = new CSVReader(reader);
      List<String[]> decodedCsv = csvReader.readAll();
      System.out.println(decodedCsv);
    } catch (Exception e){
      e.printStackTrace();
    }
  }


}


