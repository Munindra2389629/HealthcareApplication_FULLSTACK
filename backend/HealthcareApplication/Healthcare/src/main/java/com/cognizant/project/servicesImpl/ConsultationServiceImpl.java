package com.cognizant.project.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.project.DAO.ConsultationDAO;
import com.cognizant.project.DTO.ConsultationDTO;
import com.cognizant.project.controllers.NotificationController;
import com.cognizant.project.entities.Appointment;
import com.cognizant.project.entities.Appointment.Status;
import com.cognizant.project.entities.Consultation;
import com.cognizant.project.entities.User.Role;
import com.cognizant.project.exceptions.ResourceNotFoundException;
import com.cognizant.project.repositories.AppointmentRepository;
import com.cognizant.project.repositories.ConsultationRepository;
import com.cognizant.project.repositories.UserRepository;
import com.cognizant.project.services.ConsultationService;

@Service
public class ConsultationServiceImpl implements ConsultationService {

	@Autowired
	private ConsultationRepository consultationRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NotificationController notificationController;

	public String addConsultation(ConsultationDTO consultationDTO) {

		Appointment appointment = appointmentRepository.findById(consultationDTO.getAppointmentID()).orElse(null);
		if (appointment != null) {
			if (appointment.getStatus() == Status.BOOKED) {

				Consultation consultation = new Consultation();
				consultation.setNotes(consultationDTO.getNotes());
				consultation.setPrescription(consultationDTO.getPrescription());
				consultation.setAppointment(appointment);
				consultationRepository.save(consultation);
				appointment.setStatus(Status.COMPLETED);
				appointmentRepository.save(appointment);
				notificationController.notifyPatient(appointment.getPatient().getUserID(),
						"Your consultation Records for the appointment with " + appointment.getDoctor().getName()
								+ " on " + appointment.getTimeSlot() + " is available now");
				return "Consultation records are added successfully";
			}
			return "You cannot add consultation records to this appointment";
		}
		throw new ResourceNotFoundException("appointment","ID",""+consultationDTO.getAppointmentID());
	}

	public ConsultationDAO viewConsultation(int consultationID) {
		
		if (!consultationRepository.existsById(consultationID)) {

			throw new ResourceNotFoundException("consultation","ID",""+consultationID);
		}

		Consultation consultation = consultationRepository.findById(consultationID).get();
		
		ConsultationDAO consultationDAO=new ConsultationDAO();
		consultationDAO.setConsultationDate(consultation.getAppointment().getTimeSlot().toLocalDate());
		consultationDAO.setConsultationTime(consultation.getAppointment().getTimeSlot().toLocalTime());
		consultationDAO.setDoctorName(consultation.getAppointment().getDoctor().getName());
		consultationDAO.setNotes(consultation.getNotes());
		consultationDAO.setPrescription(consultation.getPrescription());
		return consultationDAO;

	}

	public List<ConsultationDAO> viewMedicalHistory(int patientID) {
		
		if (!userRepository.existsById(patientID) || userRepository.findById(patientID).get().getRole()!=Role.PATIENT) {

			throw new ResourceNotFoundException("patient","ID",""+patientID);
		}

		List<Consultation> consultations = consultationRepository.findByAppointmentPatientUserID(patientID);

		return consultations.stream().map(consultation -> {
			ConsultationDAO consultationDAO = new ConsultationDAO();
			consultationDAO.setConsultationDate(consultation.getAppointment().getTimeSlot().toLocalDate());
			consultationDAO.setConsultationTime(consultation.getAppointment().getTimeSlot().toLocalTime());
			consultationDAO.setDoctorName(consultation.getAppointment().getDoctor().getName());
			consultationDAO.setNotes(consultation.getNotes());
			consultationDAO.setPrescription(consultation.getPrescription());
			return consultationDAO;
		}).toList();

	}

}
