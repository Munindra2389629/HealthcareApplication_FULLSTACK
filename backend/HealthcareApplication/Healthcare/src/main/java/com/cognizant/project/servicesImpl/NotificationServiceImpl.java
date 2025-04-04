package com.cognizant.project.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.project.entities.Notification;
import com.cognizant.project.repositories.NotificationRepository;
import com.cognizant.project.services.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	public void notifyPatient(int patientID, String message) {
		
		Notification notification = new Notification();
		notification.setUserID(patientID);
		notification.setMessage(message);
		notificationRepository.save(notification);
	}

	public void notifyDoctor(int doctorID, String message) {
		
		Notification notification=new Notification();
		notification.setUserID(doctorID);
		notification.setMessage(message);
		notificationRepository.save(notification);
	}
	
	
}
