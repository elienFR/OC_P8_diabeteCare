package com.mediscreen.microservicepatients.service;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mediscreen.microservicepatients.model.DTO.PatientDTO;
import com.mediscreen.microservicepatients.model.Gender;
import com.mediscreen.microservicepatients.model.Patient;
import com.mediscreen.microservicepatients.model.PostalAddress;
import com.mediscreen.microservicepatients.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class PatientService {

  @Autowired
  private PatientRepository patientRepository;


  public List<PatientDTO> getAll() {
    return StreamSupport
      .stream(patientRepository.findAll().spliterator(), false)
      .map(PatientService::convertToPatientDTO)
      .collect(Collectors.toList());
  }

  /**
   *
   * this method calls the repository to insert a new patient in the database
   *
   * @param family is the patient's last name
   * @param given is the patient first name
   * @param dob is the patient's date of birth
   * @param gender is the patient's gender
   * @param address is the patient street address
   * @param phone is the patient phone
   * @return the saved patient in DB
   */
  public PatientDTO insert(String family, String given, Date dob, Gender gender, String address, String phone) {
    PatientDTO patientDTOToSave = new PatientDTO(family, given, dob, gender, address, phone);
    return insert(patientDTOToSave);
  }

  public PatientDTO insert(PatientDTO patientDTO) {
    Patient patientToSave = convertToPatient(patientDTO);
    Patient savedPatient = patientRepository.save(patientToSave);
    return convertToPatientDTO(savedPatient);
  }

  /**
   * This method converts a patientDTO into a patient
   *
   * @param patientDTOToConvert is the patient DTO to convert
   * @return a converted Patient
   */
  private static Patient convertToPatient(PatientDTO patientDTOToConvert) {
//    PostalAddress postalAddress = new PostalAddress();
//    postalAddress.setStreet(patientDTOToConvert.getAddress());
    return new Patient(
      patientDTOToConvert.getGiven(),
      patientDTOToConvert.getFamily(),
      patientDTOToConvert.getDob(),
      patientDTOToConvert.getGender(),
      patientDTOToConvert.getAddress(),
      patientDTOToConvert.getPhone());
  }

  /**
   * This method converts a patient into a patientDTO
   *
   * @param patientToConvert is the patient to convert
   * @return a converted patientDTO
   */
  private static PatientDTO convertToPatientDTO(Patient patientToConvert) {
    return new PatientDTO(
      patientToConvert.getLastname(),
      patientToConvert.getFirstname(),
      patientToConvert.getBirthdate(),
      patientToConvert.getGender(),
      patientToConvert.getAddress(),
      patientToConvert.getPhoneNumber()
    );
  }
}
