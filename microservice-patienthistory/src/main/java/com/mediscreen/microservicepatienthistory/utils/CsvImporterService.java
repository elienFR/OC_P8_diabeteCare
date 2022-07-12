package com.mediscreen.microservicepatienthistory.utils;

import com.mediscreen.microservicepatienthistory.repository.PatientHistoryRepository;
import com.mediscreen.microservicepatienthistory.service.PatientHistoryService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.bouncycastle.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvImporterService {

  public static List<String[]> parseCSV(String filepath) throws IOException, CsvException {
    FileReader reader = new FileReader(filepath);
    CSVReader csvReader = new CSVReader(reader);
    List<String[]> rawExtractedList = csvReader.readAll();
    csvReader.close();
    reader.close();
    return rawExtractedList.stream()
      .map(aoS -> Strings.split(aoS[0], ';'))
      .map(aoS -> {
          if (aoS[1].charAt(0) == '"') {
            aoS[1] = aoS[1].substring(1);
          }
          return new String[] {aoS[0], aoS[1]};
        }
      )
      .collect(Collectors.toList())
      .subList(1, rawExtractedList.size());
  }

}
