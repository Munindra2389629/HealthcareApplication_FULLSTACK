package com.cognizant.project.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.project.DAO.ConsultationDAO;
import com.cognizant.project.DTO.ConsultationDTO;
import com.cognizant.project.services.ConsultationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/consultations")
@Validated
public class ConsultationController {
		
	private ConsultationService consultationService;
	
	public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }
	
	@PostMapping("/add")
	public ResponseEntity<String> addConsultation(@Valid @RequestBody ConsultationDTO consultationDTO) {
		
		return ResponseEntity.ok(consultationService.addConsultation(consultationDTO));
	}
	
	@GetMapping("/view/all/{patientID}")
	public ResponseEntity<List<ConsultationDAO>> viewConsultations(@PathVariable int patientID){
		
		return ResponseEntity.ok(consultationService.viewMedicalHistory(patientID));
	}
	
	@GetMapping("/view/{consultationID}")
	public ResponseEntity<ConsultationDAO> viewConsultation(@PathVariable int consultationID){
		
		return ResponseEntity.ok(consultationService.viewConsultation(consultationID));
	}
		
}
