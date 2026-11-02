package com.example.demo.repository;

//import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.AppUser;
//import com.example.demo.model.AuditReport;

public interface UserRepository extends JpaRepository<AppUser, Long> {
  //  List<AuditReport> findAllByOrderByReportDateDesc();
    Optional<AppUser> findByUsername(String username);
    boolean existsByUsername(String username);

}
