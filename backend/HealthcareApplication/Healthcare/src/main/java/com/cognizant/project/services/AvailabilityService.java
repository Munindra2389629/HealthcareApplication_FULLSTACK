package com.cognizant.project.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.cognizant.project.DAO.AvailabilityDAO;

public interface AvailabilityService {

	String createAvailability(int doctorID);

	List<AvailabilityDAO> findAvailabilities();

	List<AvailabilityDAO> findAvailability(int doctorID);

	String blockAvailability(int doctorID, LocalDate date);
	
	String unBlockAvailability(int doctorID, LocalDate date);
	
	String blockAvailability(int doctorID, LocalDate date, LocalTime time);

	String unblockAvailability(int doctorID, LocalDate date, LocalTime time);

}
