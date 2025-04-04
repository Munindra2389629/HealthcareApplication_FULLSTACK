package com.cognizant.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.project.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
