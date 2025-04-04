package com.cognizant.project.entities;

import java.time.LocalTime;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TimeSlot {
    
    private LocalTime timeSlot;
    
    private boolean isBlocked;
    
}