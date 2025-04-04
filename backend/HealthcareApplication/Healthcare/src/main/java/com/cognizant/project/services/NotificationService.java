package com.cognizant.project.services;

public interface NotificationService {

	void notifyPatient(int patientID, String message);

	void notifyDoctor(int doctorID, String message);
}
