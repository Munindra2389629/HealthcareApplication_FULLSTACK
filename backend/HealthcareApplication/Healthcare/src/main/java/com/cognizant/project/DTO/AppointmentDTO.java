package com.cognizant.project.DTO;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppointmentDTO {
	
	@Min(value=1,message="Patient ID cannot be null")
	private int patientID;
	
	@Min(value=1,message="Doctor ID cannot be null")
	private int doctorID;
	
	@NotNull(message="Time slot cannot be null")
	@Future(message="Time slot must be in the future")
	private LocalDateTime timeSlot;
}
