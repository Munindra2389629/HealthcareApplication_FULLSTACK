package com.cognizant.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.project.entities.Consultation;

public interface ConsultationRepository extends JpaRepository<Consultation, Integer> {

	List<Consultation> findByAppointmentPatientUserID(int patientID);

}
