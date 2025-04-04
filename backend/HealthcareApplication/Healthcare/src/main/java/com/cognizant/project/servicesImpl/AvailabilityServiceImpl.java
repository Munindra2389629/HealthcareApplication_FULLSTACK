package com.cognizant.project.servicesImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.project.DAO.AvailabilityDAO;
import com.cognizant.project.entities.Availability;
import com.cognizant.project.entities.AvailabilityId;
import com.cognizant.project.entities.TimeSlot;
import com.cognizant.project.entities.User.Role;
import com.cognizant.project.exceptions.ResourceNotFoundException;
import com.cognizant.project.repositories.AvailabilityRepository;
import com.cognizant.project.repositories.UserRepository;
import com.cognizant.project.services.AvailabilityService;
import com.cognizant.project.services.UserService;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

	@Autowired
	private AvailabilityRepository availabilityRepository;

	private UserService userService;
	
	public AvailabilityServiceImpl(UserService userService) {
        this.userService = userService;
    }

	@Autowired
	private UserRepository userRepository;

	public String createAvailability(int doctorID) {

		if (!userRepository.existsById(doctorID) || userRepository.findById(doctorID).get().getRole()!=Role.DOCTOR) {

			throw new ResourceNotFoundException("Doctor", "ID",""+doctorID);
		}

		List<Availability> previousAvailabilities = availabilityRepository.findByDoctorID(doctorID);
		availabilityRepository.deleteAll(previousAvailabilities);

		LocalDate startDate = LocalDate.now();
		LocalDate endDate = startDate.plusDays(6);

		List<TimeSlot> timeSlots = new ArrayList<>();
		timeSlots.add(new TimeSlot(LocalTime.of(9, 0), false));
		timeSlots.add(new TimeSlot(LocalTime.of(10, 0), false));
		timeSlots.add(new TimeSlot(LocalTime.of(11, 0), false));
		timeSlots.add(new TimeSlot(LocalTime.of(13, 0), false));
		timeSlots.add(new TimeSlot(LocalTime.of(14, 0), false));
		timeSlots.add(new TimeSlot(LocalTime.of(15, 0), false));

		for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
			availabilityRepository.save(new Availability(doctorID, date, false, timeSlots));
		}
		return "Availability Created Successfully";
	}

	public String blockAvailability(int doctorID, LocalDate date) {

		if (!userRepository.existsById(doctorID)) {

			throw new ResourceNotFoundException("Doctor", "ID",""+doctorID);
		}

		Availability availability = availabilityRepository.findById(new AvailabilityId(doctorID, date)).orElse(null);
		if (availability != null) {
			if (availability.isIsblocked()) {
				return date + " is already blocked";
			}
			availability.setIsblocked(true);
			for (TimeSlot timeSlot : availability.getTimeSlots()) {
				timeSlot.setBlocked(true);
			}
			availabilityRepository.save(availability);
			return date + " is blocked successfully";
		}
		return "Availability does not exists";
	}

	public String unBlockAvailability(int doctorID, LocalDate date) {

		if (!userRepository.existsById(doctorID)) {

			throw new ResourceNotFoundException("Doctor", "ID",""+doctorID);
		}

		Availability availability = availabilityRepository.findById(new AvailabilityId(doctorID, date)).orElse(null);
		if (availability != null) {
			if (!availability.isIsblocked()) {
				return date + " is already unblocked";
			}
			availability.setIsblocked(false);
			for (TimeSlot timeSlot : availability.getTimeSlots()) {
				timeSlot.setBlocked(false);
			}
			availabilityRepository.save(availability);
			return date + " is unblocked successfully";
		}
		return "Availability does not exists";
	}

	public List<AvailabilityDAO> findAvailabilities() {

		List<Availability> availabilities = availabilityRepository.findAll();
		return availabilities.stream().filter(
				availability -> !availability.isIsblocked() && !availability.getDate().isBefore(LocalDate.now()))
				.map(availability -> {
					return new AvailabilityDAO(userService.findUser(availability.getDoctorID()).getName(),
							availability.getDate(),
							availability.getTimeSlots().stream()
									.filter(timeSlot -> !timeSlot.isBlocked()
											&& (availability.getDate().isAfter(LocalDate.now())
													|| timeSlot.getTimeSlot().isAfter(LocalTime.now())))
									.map(TimeSlot::getTimeSlot).toList());
				}).toList();
	}

	public List<AvailabilityDAO> findAvailability(int doctorID) {

		if (!userRepository.existsById(doctorID) || userRepository.findById(doctorID).get().getRole()!=Role.DOCTOR) {

			throw new ResourceNotFoundException("Doctor", "ID",""+doctorID);
		}

		List<Availability> availabilities = availabilityRepository.findByDoctorID(doctorID);

		return availabilities.stream().filter(
				availability -> !availability.isIsblocked() && !availability.getDate().isBefore(LocalDate.now()))
				.map(availability -> {
					return new AvailabilityDAO(userService.findUser(availability.getDoctorID()).getName(),
							availability.getDate(),
							availability.getTimeSlots().stream()
									.filter(timeSlot -> !timeSlot.isBlocked()
											&& (availability.getDate().isAfter(LocalDate.now())
													|| timeSlot.getTimeSlot().isAfter(LocalTime.now())))
									.map(TimeSlot::getTimeSlot).toList());
				}).toList();
	}

	public String blockAvailability(int doctorID, LocalDate date, LocalTime time) {

		if (!userRepository.existsById(doctorID)) {

			throw new ResourceNotFoundException("Doctor", "ID",""+doctorID);
		}

		Availability availability = availabilityRepository.findById(new AvailabilityId(doctorID, date)).orElse(null);

		if (availability != null) {

			TimeSlot timeSlot = availability.getTimeSlots().stream().filter(slot -> slot.getTimeSlot().equals(time))
					.findFirst().orElse(null);

			if (timeSlot != null) {
				if (timeSlot.isBlocked()) {
					return "Time Slot " + time + " on " + date + " is already blocked ";
				}
				timeSlot.setBlocked(true);
				availability.setIsblocked(availability.getTimeSlots().stream().allMatch(TimeSlot::isBlocked));
				availabilityRepository.save(availability);
				return "Time Slot " + time + " on " + date + " is blocked successfully";
			}
			return "Time Slot " + time + " on " + date + " does not exist";
		}
		return "There is no Availability";
	}

	public String unblockAvailability(int doctorID, LocalDate date, LocalTime time) {

		if (!userRepository.existsById(doctorID)) {

			throw new ResourceNotFoundException("Doctor", "ID",""+doctorID);
		}

		Availability availability = availabilityRepository.findById(new AvailabilityId(doctorID, date)).orElse(null);

		if (availability != null) {

			TimeSlot timeSlot = availability.getTimeSlots().stream().filter(slot -> slot.getTimeSlot().equals(time))
					.findFirst().orElse(null);

			if (timeSlot != null) {

				if (!timeSlot.isBlocked()) {
					return "Time Slot " + time + " on " + date + " is already unblocked";
				}
				timeSlot.setBlocked(false);
				availability.setIsblocked(availability.getTimeSlots().stream().allMatch(TimeSlot::isBlocked));
				availabilityRepository.save(availability);
				return "Time Slot " + time + " on " + date + " is unblocked successfully";
			}
			return "Time Slot " + time + " on " + date + " does not exist";
		}
		return "There is no Availability";
	}
}
