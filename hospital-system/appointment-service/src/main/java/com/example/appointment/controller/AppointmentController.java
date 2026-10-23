package com.example.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.appointment.domain.Appointment;
import com.example.appointment.domain.CreateAppointmentRequest;
import com.example.appointment.service.AppointmentService;
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
	//@Autowired
	private  AppointmentService service;

	public AppointmentController(AppointmentService service) {
		super();
		this.service = service;
	}
	
	@PostMapping("/createAppointment")
	@ResponseStatus(HttpStatus.CREATED)
	public Appointment create(@RequestBody CreateAppointmentRequest request) {
		return service.create(request);
	}
	
	@GetMapping("/{id}")
	public Appointment findById(@PathVariable Long id) {
		return service.findById(id);
	}

    
}
