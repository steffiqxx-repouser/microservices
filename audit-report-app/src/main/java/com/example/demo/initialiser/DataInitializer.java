package com.example.demo.initialiser;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Role;
import com.example.demo.model.AuditReport;
//import com.example.demo.repository.AuditReportRepository;
import com.example.demo.repository.AuditRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeData(
            UserRepository userRepository,
            RoleRepository roleRepository,
            AuditRepository reportRepository,
            PasswordEncoder passwordEncoder) {

        return args -> initialize(
                userRepository,
                roleRepository,
                reportRepository,
                passwordEncoder
        );
    }

    @Transactional
    public void initialize(
            UserRepository userRepository,
            RoleRepository roleRepository,
            AuditRepository reportRepository,
            PasswordEncoder passwordEncoder) {

        Role userRole = roleRepository
                .findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ROLE_USER");
                    return roleRepository.save(role);
                });

        if (!userRepository.existsByUsername("admin")) {

            AppUser user = new AppUser();

            user.setUsername("admin");
            user.setPassword(
                passwordEncoder.encode("Admin@123")
            );
            user.setEnabled(true);
            user.setRoles(Set.of(userRole));

            userRepository.save(user);
        }

        if (reportRepository.count() == 0) {

            AuditReport report1 = new AuditReport();
            report1.setTitle("Infrastructure Security Audit");
            report1.setReportDate(LocalDate.of(2026, 10, 10));
            report1.setStatus("COMPLETED");
            report1.setDescription(
                "Infrastructure security assessment"
            );

            AuditReport report2 = new AuditReport();
            report2.setTitle("Database Compliance Audit");
            report2.setReportDate(LocalDate.of(2026, 10, 15));
            report2.setStatus("PENDING");
            report2.setDescription(
                "Database compliance verification"
            );

            AuditReport report3 = new AuditReport();
            report3.setTitle("Application Access Audit");
            report3.setReportDate(LocalDate.of(2026, 10, 20));
            report3.setStatus("COMPLETED");
            report3.setDescription(
                "Application access review"
            );

            reportRepository.saveAll(
                java.util.List.of(report1, report2, report3)
            );
        }
    }
}