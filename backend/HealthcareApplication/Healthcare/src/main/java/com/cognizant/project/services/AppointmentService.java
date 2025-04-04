package com.cognizant.project.services;

import java.util.List;

import com.cognizant.project.DTO.AppointmentDTO;
import com.cognizant.project.entities.Appointment;

public interface AppointmentService {

	String bookAppointment(AppointmentDTO appointmentDTO);

	String cancelAppointment(int appointmentID);
	
	String modifyAppointment(int appointmentID, AppointmentDTO newAppointmentDTO); 
	
	List<Appointment> patientAppointments(int patientID);

	List<Appointment> doctorAppointments(int doctorID);
}
