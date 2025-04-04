package com.cognizant.project.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ConsultationDTO {
	
	@Min(value=1,message="Appointment ID cannot be null")
	private int appointmentID;
	
	@NotBlank(message="Notes cannot be blank")
	@Size(min=5,message="Notes must be atleast 5 characters")
	private String notes;
	
	@NotBlank(message="Prescription cannot be blank")
	@Size(min=5,message="Prescription must be atleast 5 characters")
	private String prescription;
}
