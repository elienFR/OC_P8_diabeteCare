package com.mediscreen.microservicepatienthistory.repository;

import com.mediscreen.microservicepatienthistory.model.PatientHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientHistoryRepository extends MongoRepository<PatientHistory, String> {

  Optional<List<PatientHistory>> findByPatId(String patId);

}
