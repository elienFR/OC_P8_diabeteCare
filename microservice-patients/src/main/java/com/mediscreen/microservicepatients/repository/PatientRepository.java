package com.mediscreen.microservicepatients.repository;

import com.mediscreen.microservicepatients.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer> {

  Optional<Patient> findByLastnameAndFirstnameAndBirthdate(String lastname, String firstname, Date birthdate);

  Optional<Patient> findByLastname(String lastname);

  Page<Patient> findAll(Pageable pageable);
}
