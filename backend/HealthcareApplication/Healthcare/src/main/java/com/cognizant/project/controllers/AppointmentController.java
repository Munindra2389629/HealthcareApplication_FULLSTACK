package com.cognizant.project.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.project.DTO.AppointmentDTO;
import com.cognizant.project.entities.Appointment;
import com.cognizant.project.services.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appointments")
@Validated
public class AppointmentController {

	private final AppointmentService appointmentService;
	
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

	@PostMapping("/patient/book")
	public ResponseEntity<String> bookAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
       System.out.println(appointmentDTO);
		return ResponseEntity.ok(appointmentService.bookAppointment(appointmentDTO));
	}
	
	/*
	 * @GetMapping("/findall") public List<Appointment> findall(){
	 * 
	 * return appointmentService.findAll(); }
	 */
	
	@PutMapping("/patient/cancel/{appointmentID}")
	public ResponseEntity<String> cancelAppointment(@PathVariable int appointmentID) {
		
		return ResponseEntity.ok(appointmentService.cancelAppointment(appointmentID));
	}
	
	@PutMapping("/patient/modify/{appointmentID}")
	public ResponseEntity<String> modifyAppointment(@PathVariable int appointmentID,@Valid @RequestBody AppointmentDTO appointmentDTO){
		
		return ResponseEntity.ok(appointmentService.modifyAppointment(appointmentID,appointmentDTO));
	}
	
	@GetMapping("/patient/{patientID}")
	public ResponseEntity<List<Appointment>> patientAppointments(@PathVariable int patientID){
		
		return ResponseEntity.ok(appointmentService.patientAppointments(patientID));
	}
	
	@GetMapping("/doctor/{doctorID}")
	public ResponseEntity<List<Appointment>> doctorAppointments(@PathVariable int doctorID){
		
		return ResponseEntity.ok(appointmentService.doctorAppointments(doctorID));
	}
}
