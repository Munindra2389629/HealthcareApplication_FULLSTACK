package com.cognizant.project.services;

import java.util.List;

import com.cognizant.project.DAO.ConsultationDAO;
import com.cognizant.project.DTO.ConsultationDTO;

public interface ConsultationService {

	String addConsultation(ConsultationDTO consultationDTO);

	ConsultationDAO viewConsultation(int consultationID);

	List<ConsultationDAO> viewMedicalHistory(int patientID);
}
