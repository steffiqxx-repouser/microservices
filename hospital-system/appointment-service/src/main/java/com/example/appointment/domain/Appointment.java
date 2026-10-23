package com.example.appointment.domain;

import java.time.LocalDateTime;

public class Appointment {
	private Long id;
	private Long patientid;
	private Long doctorId;
	private LocalDateTime scheduledAt;
	private String status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPatientid() {
		return patientid;
	}
	public void setPatientid(Long patientid) {
		this.patientid = patientid;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public LocalDateTime getScheduledAt() {
		return scheduledAt;
	}
	public void setScheduledAt(LocalDateTime scheduledAt) {
		this.scheduledAt = scheduledAt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Appointment(Long id, Long patientid, Long doctorId, LocalDateTime scheduledAt, String status) {
		super();
		this.id = id;
		this.patientid = patientid;
		this.doctorId = doctorId;
		this.scheduledAt = scheduledAt;
		this.status = status;
	}

}
