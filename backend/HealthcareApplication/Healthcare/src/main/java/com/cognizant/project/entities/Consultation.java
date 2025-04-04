package com.cognizant.project.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Consultation {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int consultationID;
	
	@OneToOne
	@JoinColumn(name="appointmentID")
	private Appointment appointment;
	
	private String notes;
	private String prescription;
	
}
