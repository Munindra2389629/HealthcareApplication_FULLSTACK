package com.cognizant.project.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.project.DAO.AvailabilityDAO;
import com.cognizant.project.services.AvailabilityService;

@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {
	
	private AvailabilityService availabilityService;
	
    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }
	
	@PostMapping("/add/{doctorID}")
	public ResponseEntity<String> createAvailability(@PathVariable int doctorID) {
		
		return ResponseEntity.ok(availabilityService.createAvailability(doctorID));
	}  
	
	@GetMapping("/find/all")
	public ResponseEntity<List<AvailabilityDAO>> findAvailabilities(){
		
		return ResponseEntity.ok(availabilityService.findAvailabilities());
	}   
	
	@GetMapping("/find/{doctorID}")
	public ResponseEntity<List<AvailabilityDAO>> findAvailability(@PathVariable int doctorID){
		
		return ResponseEntity.ok(availabilityService.findAvailability(doctorID));
	}
	
	@PutMapping("/block/{doctorID}/{date}")
	public ResponseEntity<String> blockAvailability(@PathVariable int doctorID,
			@PathVariable @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date) {
		
		return ResponseEntity.ok(availabilityService.blockAvailability(doctorID,date));
	}
	
	@PutMapping("/unblock/{doctorID}/{date}")
	public ResponseEntity<String> unBlockAvailability(@PathVariable int doctorID,
			@PathVariable @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date) {
		
		return ResponseEntity.ok(availabilityService.unBlockAvailability(doctorID,date));
	}
	
	@PutMapping("/block/{doctorID}/{date}/{timeSlot}")
	public ResponseEntity<String> blockAvailability(@PathVariable int doctorID,
	                                @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
	                                @PathVariable @DateTimeFormat(pattern = "HH:mm:ss") LocalTime timeSlot) {
	    return ResponseEntity.ok(availabilityService.blockAvailability(doctorID, date, timeSlot));
	}
	
	@PutMapping("/unblock/{doctorID}/{date}/{timeSlot}")
	public ResponseEntity<String> unblockAvailability(@PathVariable int doctorID,
	                                @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
	                                @PathVariable @DateTimeFormat(pattern = "HH:mm:ss") LocalTime timeSlot) {
	    return ResponseEntity.ok(availabilityService.unblockAvailability(doctorID, date, timeSlot));
	}
}
