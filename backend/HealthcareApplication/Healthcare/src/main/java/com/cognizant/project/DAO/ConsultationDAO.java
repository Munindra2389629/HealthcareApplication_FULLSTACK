package com.cognizant.project.DAO;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class ConsultationDAO {
	
	private LocalDate consultationDate;
	private LocalTime consultationTime;
	private String doctorName;
	private String notes;
	private String prescription;
}
