package com.cognizant.project.controllers;


import org.springframework.stereotype.Controller;

import com.cognizant.project.services.NotificationService;

@Controller
public class NotificationController {
	
	private NotificationService notificationService;
	
	public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
	
	public void notifyPatient(int patientID,String message) {
		
		notificationService.notifyPatient(patientID,message);
	}
	
	public void notifyDoctor(int doctorID,String message) {
		
		notificationService.notifyDoctor(doctorID,message);
	}
}
