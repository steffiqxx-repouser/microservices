package com.example.registryauditmanagementsystem.service;

import com.example.registryauditmanagementsystem.entity.Employee;
import com.example.registryauditmanagementsystem.repository.EmployeeRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	public Employee findById(Long id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
	}

	public Employee create(Employee employee) {
		employee.setId(null);
		return employeeRepository.save(employee);
	}

	public Employee update(Long id, Employee employee) {
		Employee existingEmployee = findById(id);
		existingEmployee.setName(employee.getName());
		existingEmployee.setEmail(employee.getEmail());
		existingEmployee.setDepartment(employee.getDepartment());
		return employeeRepository.save(existingEmployee);
	}

	public void delete(Long id) {
		employeeRepository.delete(findById(id));
	}
}
