package com.example.registryauditmanagementsystem.repository;

import com.example.registryauditmanagementsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
