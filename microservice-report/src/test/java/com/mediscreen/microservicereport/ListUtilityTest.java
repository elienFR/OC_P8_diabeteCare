package com.mediscreen.microservicereport;

import com.mediscreen.microservicereport.service.utilities.ListUtilityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ListUtilityTest {

  @Autowired
  private ListUtilityService listUtilityServiceUnderTest;

  @Test
  public void numberOfTriggerWordsTest() {
    List<String> givenTriggerList = new ArrayList<>(List.of("trigger1", "trigger2", "trigger3"));
    List<String> givenAnalysedList = new ArrayList<>(
      List.of(
        "i am a sentence that will trigger trigger1 once",
        "i am a sentence that will trigger trigger1 trigger1 once even if it is written twice",
        "i am a sentence that will trigger trigger2 once",
        "And normally trigger 3 will never be triggered",
        "so it won't be counted"
      )
    );

    Map<String, Integer> results = ListUtilityService.numberOfTriggerWords(givenAnalysedList, givenTriggerList);

    results.forEach((s, i) -> System.out.println(s + " --> " + i));

    assertThat(results.get(givenTriggerList.get(0))).isEqualTo(2);
    assertThat(results.get(givenTriggerList.get(1))).isEqualTo(1);
    assertThat(results.get(givenTriggerList.get(2))).isNull();
  }

}
