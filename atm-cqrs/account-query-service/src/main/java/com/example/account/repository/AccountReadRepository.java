package com.example.account.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.account.entity.AccountReadModel;

public interface AccountReadRepository extends JpaRepository<AccountReadModel, Long> {
    boolean existsByLastEventId(UUID eventId);

}
