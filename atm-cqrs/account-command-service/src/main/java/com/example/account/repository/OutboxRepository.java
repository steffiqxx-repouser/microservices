package com.example.account.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.account.entity.OutboxEvent;

public interface OutboxRepository extends JpaRepository<OutboxEvent, UUID> {
	List<OutboxEvent> findTop100ByPublishedFalseOrderByCreatedAtAsc();
}
