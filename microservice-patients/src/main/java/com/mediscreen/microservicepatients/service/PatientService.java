package com.mediscreen.microservicepatients.service;

import com.mediscreen.microservicepatients.model.DTO.PatientDTO;
import com.mediscreen.microservicepatients.model.Gender;
import com.mediscreen.microservicepatients.model.Patient;
import com.mediscreen.microservicepatients.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
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
      .map(patient -> convertToPatientDTO(Optional.of(patient)).get())
      .collect(Collectors.toList());
  }

  /**
   * this method calls the repository to insert a new patient in the database
   *
   * @param family      is the patient's last name
   * @param given       is the patient first name
   * @param birthdate   is the patient's date of birth
   * @param gender      is the patient's gender
   * @param address     is the patient street address
   * @param phone       is the patient phone
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
    return insert(patientDTOToSave);
  }

  public PatientDTO insert(PatientDTO patientDTO) {
    Patient patientToSave = convertToPatient(Optional.of(patientDTO)).get();
    Patient savedPatient = patientRepository.save(patientToSave);
    return convertToPatientDTO(Optional.of(savedPatient)).get();
  }

  /**
   * This method converts a patientDTO into a patient
   *
   * @param patientDTOToConvert is the patient DTO to convert
   * @return a converted Patient
   */
  private static Optional<Patient> convertToPatient(Optional<PatientDTO> patientDTOToConvert) {
    if (patientDTOToConvert.isEmpty()) {
      return Optional.of(new Patient());
    } else {
      return Optional.of(new Patient(
        patientDTOToConvert.get().getGiven(),
        patientDTOToConvert.get().getFamily(),
        patientDTOToConvert.get().getDob(),
        patientDTOToConvert.get().getGender(),
        patientDTOToConvert.get().getAddress(),
        patientDTOToConvert.get().getPhone())
      );
    }
  }

  /**
   * This method converts a patient into a patientDTO
   *
   * @param patientToConvert is the patient to convert
   * @return a converted patientDTO
   */
  private static Optional<PatientDTO> convertToPatientDTO(Optional<Patient> patientToConvert) {
    if (patientToConvert.isEmpty()) {
      return Optional.of(new PatientDTO());
    } else {
      return Optional.of(new PatientDTO(
        patientToConvert.get().getLastname(),
        patientToConvert.get().getFirstname(),
        patientToConvert.get().getBirthdate(),
        patientToConvert.get().getGender(),
        patientToConvert.get().getAddress(),
        patientToConvert.get().getPhoneNumber()
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
    return convertToPatientDTO(
      findPatientByLastnameAndFirstnameAndBirthdate(lastname, firstname, birthdateSQL));
  }

  private Date convertInDateSql(String dateString) {
    java.util.Date dateUtil = null;
    try {
      dateUtil = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    Date dateSql = new Date(dateUtil.getTime());
    return dateSql;
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
    if(optionalPatientToDelete.isPresent()){
      patientRepository.delete(optionalPatientToDelete.get());
      return true;
    } else {
      return false;
    }
  }
}
