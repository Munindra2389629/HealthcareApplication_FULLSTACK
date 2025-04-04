package com.cognizant.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.project.entities.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer>{

}
