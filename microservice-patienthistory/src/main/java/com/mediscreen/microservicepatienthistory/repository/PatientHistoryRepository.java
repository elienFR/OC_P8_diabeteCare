package com.mediscreen.microservicepatienthistory.repository;

import com.mediscreen.microservicepatienthistory.model.PatientHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientHistoryRepository extends MongoRepository<PatientHistory, String> {
  List<PatientHistory> findByPatId(String patId);

  Page<PatientHistory> findByPatId(String patId, Pageable pageable);


}
