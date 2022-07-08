package com.mediscreen.microservicereport.service.utilities;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ListUtilityService {

  public static Map<String, Integer> numberOfTriggerWords(List<String> analysedList, List<String> triggerList) {
    Map<String, Integer> foundTriggerMap = new TreeMap<>();

    for (String triggerWord : triggerList) {
      analysedList.forEach(
        stringFromList -> {
          if (stringFromList.contains(triggerWord)) {
            if (!foundTriggerMap.keySet().contains(triggerWord)) {
              foundTriggerMap.put(triggerWord, 1);
            } else {
              foundTriggerMap.put(
                triggerWord,
                foundTriggerMap.get(triggerWord) + 1
              );
            }
          }
        }
      );
    }
    return foundTriggerMap;
  }
}
