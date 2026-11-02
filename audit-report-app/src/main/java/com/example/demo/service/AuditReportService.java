package com.example.demo.service;

import java.util.List;

//import com.example.demo.entity.AuditReport;
//import com.example.demo.repository.AuditReportRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.AuditReport;
import com.example.demo.repository.AuditRepository;

@Service
@Transactional(readOnly = true)
public class AuditReportService {

    private final AuditRepository repository;

    public AuditReportService(
    		AuditRepository repository) {
        this.repository = repository;
    }

    public List<AuditReport> getAllReports() {
        return repository.findAllByOrderByReportDateDesc();
    }
}