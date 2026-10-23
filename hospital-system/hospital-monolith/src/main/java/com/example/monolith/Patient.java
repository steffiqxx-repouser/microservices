package com.example.monolith;

public class Patient {
private Long id;
public Patient(Long id, String name) {
	super();
	this.id = id;
	this.name = name;
}
private String name;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
}
