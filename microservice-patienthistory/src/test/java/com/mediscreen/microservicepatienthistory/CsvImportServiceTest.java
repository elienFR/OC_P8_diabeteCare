package com.mediscreen.microservicepatienthistory;


import com.mediscreen.microservicepatienthistory.utils.CsvImporterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvImportServiceTest {

  @Test
  public void testCsvReader() throws Exception {
    String givenFilepath = "resources/testFile.csv";

    List<String[]> result = CsvImporterService.parseCSV(givenFilepath);

    assertThat(result.size()).isEqualTo(7);
    result.forEach(
      aoS -> assertThat(aoS.length == 2).isTrue());
  }


}


