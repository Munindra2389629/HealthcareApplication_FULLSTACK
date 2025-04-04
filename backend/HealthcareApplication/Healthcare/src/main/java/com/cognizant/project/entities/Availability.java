package com.cognizant.project.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AvailabilityId.class)
@Table(name = "Availability", uniqueConstraints = @UniqueConstraint(columnNames = {"doctorID", "date"}))
public class Availability {
	
	@Id
	private int doctorID;
	
	@Id
	private LocalDate date;
	
	private boolean isblocked;
	
	@ElementCollection
	@CollectionTable(name = "TimeSlots", 
		joinColumns ={ @JoinColumn(name = "doctorID",referencedColumnName="doctorID"), 
					   @JoinColumn(name = "date",referencedColumnName="date")})
	private List<TimeSlot> timeSlots;
}
