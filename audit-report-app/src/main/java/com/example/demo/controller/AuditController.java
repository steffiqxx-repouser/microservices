
package com.example.demo.controller;

import com.example.demo.service.AuditReportService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuditController {

    private final AuditReportService auditReportService;

    public AuditController(
            AuditReportService auditReportService) {
        this.auditReportService = auditReportService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/reports";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/reports")
    public String reports(Model model) {

        model.addAttribute(
            "reports",
            auditReportService.getAllReports()
        );

        return "reports";
    }
}
