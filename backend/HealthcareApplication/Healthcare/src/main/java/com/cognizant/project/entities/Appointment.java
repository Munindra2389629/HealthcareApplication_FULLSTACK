package com.cognizant.project.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Appointment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int appointmentID;
	
	@ManyToOne
	@JoinColumn(name="patientID")
	private User patient;
	
	@ManyToOne
	@JoinColumn(name="doctorID")
	private User doctor;
	
	private LocalDateTime timeSlot;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	public enum Status{
		BOOKED,CANCELLED,COMPLETED 
	}
}