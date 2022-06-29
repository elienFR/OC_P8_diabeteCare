package com.mediscreen.microservicepatients.service;

import com.mediscreen.microservicepatients.controller.exceptions.AlreadyExistException;
import com.mediscreen.microservicepatients.controller.exceptions.PatientNotFoundException;
import com.mediscreen.microservicepatients.model.DTO.PatientDTO;
import com.mediscreen.microservicepatients.model.Gender;
import com.mediscreen.microservicepatients.model.Patient;
import com.mediscreen.microservicepatients.repository.PatientRepository;
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

  @Autowired
  private PatientRepository patientRepository;


  public List<PatientDTO> getAll() {
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
      return insert(patientDTOToSave);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new AlreadyExistException("This patient already exists");
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
   * @param birthDate is the patient date of birth
   * @return a patient DTO object
   */
  public Optional<PatientDTO> findPatientDTOByLastnameAndFirstnameAndBirthdate(
    String lastname,
    String firstname,
    String birthDate) {
    Date birthdateSQL = convertInDateSql(birthDate);
    Optional<Patient> optionalPatient =
      findPatientByLastnameAndFirstnameAndBirthdate(lastname, firstname, birthdateSQL);
    if (optionalPatient.isPresent()) {
      return convertToPatientDTO(optionalPatient.get());
    } else {
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

  private Optional<Patient> findPatientByLastnameAndFirstnameAndBirthdate(
    String lastname,
    String firstname,
    Date birthdate) {

    return patientRepository.findByLastnameAndFirstnameAndBirthdate(lastname, firstname, birthdate);

  }


  public boolean delete(String lastname, String firstname, String birthdate) {
    Optional<Patient> optionalPatientToDelete = findPatientByLastnameAndFirstnameAndBirthdate(
      lastname,
      firstname,
      convertInDateSql(birthdate)
    );
    if (optionalPatientToDelete.isPresent()) {
      patientRepository.delete(optionalPatientToDelete.get());
      return true;
    } else {
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
    Optional<Patient> optionalPatient = findPatientByLastnameAndFirstnameAndBirthdate(
      lastname,
      firstname,
      convertInDateSql(birthdate)
    );
    if (optionalPatient.isPresent()) {
      // an update do not change first name, lastname, and birthdate as they are used as unique key in DB.
      Patient patientToUpdate = optionalPatient.get();
      patientToUpdate.setGender(gender);
      patientToUpdate.setAddress(address);
      patientToUpdate.setPhoneNumber(phone);
      Optional<PatientDTO> optionalPatientDTOConverted = convertToPatientDTO(save(patientToUpdate));
      return optionalPatientDTOConverted.orElse(null);
    } else {
      throw new PatientNotFoundException("The patient you try to update has not been found !");
    }

  }

  private Patient save(Patient patient) {
    return patientRepository.save(patient);
  }

}
