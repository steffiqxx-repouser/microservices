package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.AuditReport;

public interface AuditRepository extends JpaRepository<AuditReport, Long> {
    List<AuditReport> findAllByOrderByReportDateDesc();


}
