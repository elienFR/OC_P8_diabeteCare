package com.mediscreen.microservicepatients.service;

import com.mediscreen.microservicepatients.exceptions.AlreadyExistsException;
import com.mediscreen.microservicepatients.exceptions.PatientNotFoundException;
import com.mediscreen.microservicepatients.model.DTO.PatientDTO;
import com.mediscreen.microservicepatients.model.Gender;
import com.mediscreen.microservicepatients.model.Patient;
import com.mediscreen.microservicepatients.repository.PatientRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class PatientService {

  private static final Logger LOGGER = LogManager.getLogger(PatientService.class);

  @Autowired
  private PatientRepository patientRepository;


  public List<PatientDTO> getAll() {
    LOGGER.info("Fetching all patients in DB.");
    return StreamSupport
      .stream(patientRepository.findAll().spliterator(), false)
      .map(patient -> {
        Optional<PatientDTO> optPatientDtoConverted = convertToPatientDTO(patient);
        return optPatientDtoConverted.orElse(Optional.of(new PatientDTO()).get());
      })
      .collect(Collectors.toList());
  }

  /**
   * this method calls the repository to insert a new patient in the database
   *
   * @param family    is the patient's last name
   * @param given     is the patient first name
   * @param birthdate is the patient's date of birth
   * @param gender    is the patient's gender
   * @param address   is the patient street address
   * @param phone     is the patient phone
   * @return the saved patient in DB
   */
  public PatientDTO insert(String family, String given, String birthdate, Gender gender, String address, String phone) {
    PatientDTO patientDTOToSave =
      new PatientDTO(
        family,
        given,
        convertInDateSql(birthdate),
        gender,
        address,
        phone
      );
    try {
      LOGGER.info("Trying to save patient.");
      return insert(patientDTOToSave);
    } catch (SQLException e) {
      e.printStackTrace();
      LOGGER.warn("Error occured. Throwing exception AlreadyExistsException");
      throw new AlreadyExistsException("This patient already exists");
    }
  }

  private PatientDTO insert(PatientDTO patientDTO) throws SQLException {
    Optional<Patient> optPatientToSave = convertToPatient(patientDTO);
    if (optPatientToSave.isPresent()) {
      Patient savedPatient = patientRepository.save(optPatientToSave.get());
      Optional<PatientDTO> optSavedPatientDTO = convertToPatientDTO(savedPatient);
      return optSavedPatientDTO.orElse(null);
    } else {
      return null;
    }
  }

  /**
   * This method converts a patientDTO into a patient
   *
   * @param patientDTOToConvert is the patient DTO to convert
   * @return a converted Patient
   */
  private static Optional<Patient> convertToPatient(PatientDTO patientDTOToConvert) {
    if (Objects.isNull(patientDTOToConvert)) {
      return Optional.empty();
    } else {
      return Optional.of(new Patient(
        patientDTOToConvert.getGiven(),
        patientDTOToConvert.getFamily(),
        patientDTOToConvert.getDob(),
        patientDTOToConvert.getGender(),
        patientDTOToConvert.getAddress(),
        patientDTOToConvert.getPhone())
      );
    }
  }

  /**
   * This method converts a patient into a patientDTO
   *
   * @param patientToConvert is the patient to convert
   * @return a converted patientDTO
   */
  private static Optional<PatientDTO> convertToPatientDTO(Patient patientToConvert) {
    if (Objects.isNull(patientToConvert)) {
      return Optional.empty();
    } else {
      return Optional.of(new PatientDTO(
        patientToConvert.getLastname(),
        patientToConvert.getFirstname(),
        patientToConvert.getBirthdate(),
        patientToConvert.getGender(),
        patientToConvert.getAddress(),
        patientToConvert.getPhoneNumber()
      ));
    }
  }

  /**
   * Find a patient thanks to its firstname lastname and date of birth
   *
   * @param lastname  is the patient family name
   * @param firstname is the patient firstname
   * @param birthdate is the patient date of birth
   * @return a patient DTO object
   */
  public Optional<PatientDTO> findPatientDTOByLastnameAndFirstnameAndBirthdate(
    String lastname,
    String firstname,
    String birthdate) {
    LOGGER.info("Fetching patient : " +
      "lastname : " + lastname +
      "firstname : " + firstname +
      "birthdate : " + birthdate +
      ".");
    Optional<Patient> optionalPatient =
      findPatientByLastnameAndFirstnameAndBirthdate(lastname, firstname, birthdate);
    if (optionalPatient.isPresent()) {
      LOGGER.info("Patient found !");
      return convertToPatientDTO(optionalPatient.get());
    } else {
      LOGGER.info("No patient found in DB !");
      return Optional.empty();
    }
  }

  private Date convertInDateSql(String dateString) {
    java.util.Date dateUtil;
    try {
      dateUtil = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    return new Date(dateUtil.getTime());
  }

  private Optional<Patient> findPatientByLastnameAndFirstnameAndBirthdate(String lastname,
                                                                          String firstname,
                                                                          String birthdate) {
    return patientRepository.findByLastnameAndFirstnameAndBirthdate(
      lastname,
      firstname,
      convertInDateSql(birthdate)
    );
  }


  public boolean delete(String lastname, String firstname, String birthdate) {
    LOGGER.info("Deleting process started. Finding patient with " +
      "lastname=" + lastname +
      ", firstname=" + firstname +
      ", birthdate=" + birthdate +
      "...");
    Optional<Patient> optionalPatientToDelete = findPatientByLastnameAndFirstnameAndBirthdate(
      lastname,
      firstname,
      birthdate
    );
    if (optionalPatientToDelete.isPresent()) {
      LOGGER.info("Patient found in DB. Deleting...");
      patientRepository.delete(optionalPatientToDelete.get());
      return true;
    } else {
      LOGGER.warn("Deletion abort because patient was not found...");
      return false;
    }
  }

  public PatientDTO update(
    String lastname,
    String firstname,
    String birthdate,
    Gender gender,
    String address,
    String phone) {
    LOGGER.info("Finding already existing patient...");
    Optional<Patient> optionalPatient = findPatientByLastnameAndFirstnameAndBirthdate(
      lastname,
      firstname,
      birthdate
    );
    if (optionalPatient.isPresent()) {
      LOGGER.info("Patient found.");
      // an update do not change first name, lastname, and birthdate as they are used as unique key in DB.
      LOGGER.info("Updating already existing patient...");
      Patient patientToUpdate = optionalPatient.get();
      patientToUpdate.setGender(gender);
      patientToUpdate.setAddress(address);
      patientToUpdate.setPhoneNumber(phone);
      LOGGER.info("Saving updated patient...");
      Optional<PatientDTO> optionalPatientDTOConverted = convertToPatientDTO(save(patientToUpdate));
      LOGGER.info("Updated patient saved !");
      return optionalPatientDTOConverted.orElse(null);
    } else {
      LOGGER.warn("Patient not found. Throwing exception.");
      throw new PatientNotFoundException("The patient you try to update has not been found !");
    }

  }

  private Patient save(Patient patient) {
    LOGGER.info("Saving new patient to DB. \n" + patient);
    if (Objects.isNull(patient.getId())) {
      return patientRepository.save(patient);
    }
    LOGGER.error("You cannot save a new patient with a non null id.");
    return null;
  }

  public Integer getId(String lastname, String firstname, String birthdate) {
    LOGGER.info("Beginning .getId(...) process...");
    Optional<Patient> optPatient =
      findPatientByLastnameAndFirstnameAndBirthdate(lastname, firstname, birthdate);
    if (optPatient.isPresent()) {
      Integer patId = optPatient.get().getId();
      LOGGER.info("Patient found, patId=" + patId + ".");
      return patId;
    }
    LOGGER.warn("No patient found. Id provided will be null.");
    return null;
  }

  public Patient findById(Integer patId) {
    LOGGER.info("Contacting DB to look for patient...");
    Optional<Patient> optionalPatient = patientRepository.findById(patId);
    if (optionalPatient.isPresent()) {
      LOGGER.info("Patient found !");
      return optionalPatient.get();
    }
    LOGGER.warn("Patient not found.");
    throw new PatientNotFoundException("Patient not found");
  }
}
