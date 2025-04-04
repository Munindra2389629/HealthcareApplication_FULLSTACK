package com.cognizant.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.project.entities.Availability;
import com.cognizant.project.entities.AvailabilityId;

public interface AvailabilityRepository extends JpaRepository<Availability, AvailabilityId> {

	List<Availability> findByDoctorID(int doctorID);
	
}
