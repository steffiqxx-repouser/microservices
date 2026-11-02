
package com.example.demo.model;

public class AuditReport {

    private Long id;
    private String title;
    private String reportDate;
    private String status;

    public AuditReport(Long id, String title,
                       String reportDate, String status) {
        this.id = id;
        this.title = title;
        this.reportDate = reportDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReportDate() {
        return reportDate;
    }

    public String getStatus() {
        return status;
    }
}
