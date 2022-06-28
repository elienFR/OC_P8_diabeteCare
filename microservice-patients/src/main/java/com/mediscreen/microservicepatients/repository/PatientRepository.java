package com.mediscreen.microservicepatients.repository;

import com.mediscreen.microservicepatients.model.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer> {

  Patient findByLastnameAndFirstnameAndBirthdate(String lastname, String firstname, Date birthdate);

}
