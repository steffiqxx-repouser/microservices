package com.example.outbox.infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.outbox.domain.OutboxEvent;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {
	   List<OutboxEvent>
	    findTop100ByPublishedAtIsNullOrderByCreatedAtAsc();
}
