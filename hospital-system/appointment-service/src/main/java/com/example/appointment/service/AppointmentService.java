package com.example.appointment.service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.appointment.domain.Appointment;
import com.example.appointment.domain.CreateAppointmentRequest;
@Service
public class AppointmentService {
	private AtomicLong sequence = new AtomicLong(100);
	private ConcurrentHashMap<Long, Appointment> appointments = new ConcurrentHashMap<Long, Appointment>();

	public Appointment create(CreateAppointmentRequest request) {
		Appointment appointment = new Appointment(sequence.getAndIncrement(), request.getPatientId(),
				request.getDoctorId(), request.getScheduledAt(), "BOOKED");
		// appointments[appointment.setId(null);]
		appointments.put(appointment.getId(), appointment);
		return appointment;
	}

	public Appointment findById(Long id) {
		return appointments.get(id);
	}

	public List<Appointment> getAllAppointments() {
		return (List<Appointment>) appointments.values();
	}
}
