
package com.example.demo.service;

import com.example.demo.model.AuditReport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditReportService {

    public List<AuditReport> getAllReports() {

        return List.of(
            new AuditReport(
                1001L,
                "Infrastructure Security Audit",
                "2026-10-10",
                "COMPLETED"
            ),
            new AuditReport(
                1002L,
                "Database Compliance Audit",
                "2026-10-15",
                "PENDING"
            ),
            new AuditReport(
                1003L,
                "Application Access Audit",
                "2026-10-20",
                "COMPLETED"
            ),
            new AuditReport(
                1004L,
                "Network Security Audit",
                "2026-10-25",
                "IN_PROGRESS"
            )
        );
    }
}
