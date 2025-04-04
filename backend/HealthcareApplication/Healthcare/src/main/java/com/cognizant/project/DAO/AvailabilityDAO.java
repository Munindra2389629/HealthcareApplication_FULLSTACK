package com.cognizant.project.DAO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityDAO {
	
	private String doctorName;
	private LocalDate date;
	private List<LocalTime> timeSlots;
}
