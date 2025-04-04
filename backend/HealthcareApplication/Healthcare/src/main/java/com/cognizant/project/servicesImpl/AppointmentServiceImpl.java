package com.cognizant.project.servicesImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.project.DAO.AvailabilityDAO;
import com.cognizant.project.DTO.AppointmentDTO;
import com.cognizant.project.entities.Appointment;
import com.cognizant.project.entities.Appointment.Status;
import com.cognizant.project.exceptions.ResourceNotFoundException;
import com.cognizant.project.entities.User;
import com.cognizant.project.repositories.AppointmentRepository;
import com.cognizant.project.repositories.UserRepository;
import com.cognizant.project.services.AppointmentService;
import com.cognizant.project.services.AvailabilityService;
import com.cognizant.project.services.NotificationService;
import com.cognizant.project.services.UserService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private UserRepository userRepository;
	
	private UserService userService;
	private AvailabilityService availabilityService;
	private NotificationService notificationService;
	
	public AppointmentServiceImpl(UserService userService,AvailabilityService availabilityService,NotificationService notificationService) {
        this.userService=userService;
		this.availabilityService = availabilityService;
		this.notificationService=notificationService;
    }

	public String bookAppointment(AppointmentDTO appointmentDTO) {

		User patient = userService.findUser(appointmentDTO.getPatientID());

		if (patient == null) {

			throw new ResourceNotFoundException("Patient", "ID", "" + "" + appointmentDTO.getPatientID());
		}

		User doctor = userService.findUser(appointmentDTO.getDoctorID());

		if (doctor == null) {

			throw new ResourceNotFoundException("Doctor", "ID", "" + appointmentDTO.getDoctorID());
		}
		List<AvailabilityDAO> availabilitiesDAO = availabilityService.findAvailability(doctor.getUserID());
		LocalDate date = appointmentDTO.getTimeSlot().toLocalDate();
		LocalTime time = appointmentDTO.getTimeSlot().toLocalTime();

		boolean isAvailable = availabilitiesDAO.stream()
				.anyMatch(availabilityDAO -> availabilityDAO.getDate().equals(date)
						&& availabilityDAO.getTimeSlots().contains(time));
		if (isAvailable) {
			availabilityService.blockAvailability(appointmentDTO.getDoctorID(), date, time);
			Appointment appointment = new Appointment();
			appointment.setPatient(patient);
			appointment.setDoctor(doctor);
			appointment.setTimeSlot(appointmentDTO.getTimeSlot());
			appointment.setStatus(Status.BOOKED);
			appointmentRepository.save(appointment);
			notificationService.notifyPatient(patient.getUserID(),
					"Your appointment has been scheduled with " + doctor.getName() + " on " + date + " " + time);
			notificationService.notifyDoctor(doctor.getUserID(),
					"You have appointment with " + patient.getName() + " on " + date + " " + time);
			return "Your appointment has been succesfully booked on " + date + " " + time + " with appointment Id: "
					+ appointment.getAppointmentID();
		}

		return "You cannot book appointment at that date and timeslot";
	}

	/*
	 * public List<Appointment> findAll() {
	 * 
	 * return appointmentRepository.findAll(); }
	 */

	public String cancelAppointment(int appointmentID) {

		Appointment appointment = appointmentRepository.findById(appointmentID)
				.orElseThrow(() -> new ResourceNotFoundException("Appointment", "ID", "" + appointmentID));

		if (appointment.getStatus() == Status.CANCELLED) {
			return "Appointment with ID: " + appointmentID + " is already cancelled";
		}
		
		if (appointment.getStatus() == Status.COMPLETED) {
			return "This appointment cannot be cancelled";
		}

		User patient = appointment.getPatient();
		User doctor = appointment.getDoctor();

		LocalDate date = appointment.getTimeSlot().toLocalDate();
		LocalTime time = appointment.getTimeSlot().toLocalTime();

		appointment.setStatus(Status.CANCELLED);

		availabilityService.unblockAvailability(doctor.getUserID(), date, time);

		appointmentRepository.save(appointment);

		notificationService.notifyPatient(patient.getUserID(), "Your appointment with " + doctor.getName() + " on "
				+ date + " " + time + " is cancelled successfully");
		notificationService.notifyDoctor(doctor.getUserID(),
				"Your appointment with " + patient.getName() + " on " + date + " " + time + " is cancelled");

		return "Your appointment with ID: " + appointmentID + " is cancelled successfully";

	}

	public String modifyAppointment(int appointmentID, AppointmentDTO newAppointmentDTO) {
		Appointment appointment = appointmentRepository.findById(appointmentID)
				.orElseThrow(() -> new ResourceNotFoundException("Appointment", "ID", "" + appointmentID));

		User doctor = userService.findUser(newAppointmentDTO.getDoctorID());
		if (doctor == null) {
			throw new ResourceNotFoundException("Doctor", "ID", "" + newAppointmentDTO.getDoctorID());
		}

		LocalDate date = newAppointmentDTO.getTimeSlot().toLocalDate();
		LocalTime time = newAppointmentDTO.getTimeSlot().toLocalTime();

		List<AvailabilityDAO> availabilitiesDAO = availabilityService.findAvailability(doctor.getUserID());
		boolean isAvailable = availabilitiesDAO.stream()
				.anyMatch(availabilityDAO -> availabilityDAO.getDate().equals(date)
						&& availabilityDAO.getTimeSlots().contains(time));

		if (!isAvailable) {
			return "The new date and timeslot are not available.";
		}

		availabilityService.unblockAvailability(appointment.getDoctor().getUserID(),
				appointment.getTimeSlot().toLocalDate(), appointment.getTimeSlot().toLocalTime());

		availabilityService.blockAvailability(newAppointmentDTO.getDoctorID(), date, time);

		appointment.setDoctor(doctor);
		appointment.setTimeSlot(newAppointmentDTO.getTimeSlot());
		appointmentRepository.save(appointment);

		notificationService.notifyPatient(appointment.getPatient().getUserID(),
				"Your appointment has been rescheduled with " + doctor.getName() + " on " + date + " " + time);
		notificationService.notifyDoctor(doctor.getUserID(), "You have a rescheduled appointment with "
				+ appointment.getPatient().getName() + " on " + date + " " + time);

		return "Your appointment has been successfully modified.";
	}

	public List<Appointment> patientAppointments(int patientID) {

		if (!userRepository.existsById(patientID)) {

			throw new ResourceNotFoundException("Patient", "ID", "" + patientID);
		}

		List<Appointment> appointments = appointmentRepository.findAll();
		return appointments.stream().filter(appointment -> appointment.getPatient().getUserID() == patientID).toList();
	}

	public List<Appointment> doctorAppointments(int doctorID) {

		if (!userRepository.existsById(doctorID)) {

			throw new ResourceNotFoundException("Doctor", "ID", "" + doctorID);
		}

		List<Appointment> appointments = appointmentRepository.findAll();
		return appointments.stream().filter(appointment -> appointment.getDoctor().getUserID() == doctorID).toList();
	}
}
