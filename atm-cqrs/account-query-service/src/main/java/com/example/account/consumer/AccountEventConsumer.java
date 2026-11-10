package com.example.account.consumer;

import org.springframework.kafka.annotation.KafkaListener;

import com.example.account.entity.AccountReadModel;
import com.example.account.events.AccountEvent;
import com.example.account.repository.AccountReadRepository;

import jakarta.transaction.Transactional;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.JacksonException;

public class AccountEventConsumer {
	 private final AccountReadRepository repository;
	    private final ObjectMapper objectMapper;

	    public AccountEventConsumer(
	            AccountReadRepository repository,
	            ObjectMapper objectMapper) {

	        this.repository = repository;
	        this.objectMapper = objectMapper;
	    }

	    @KafkaListener(
	        topics = "atm-account-events",
	        groupId = "atm-query-service"
	    )
	    @Transactional
	    public void consume(String message)
	            throws JacksonException {

	        AccountEvent event = objectMapper.readValue(
	            message,
	            AccountEvent.class
	        );

	        // Simplified duplicate protection.
	        if (repository.existsByLastEventId(event.eventId())) {
	            return;
	        }

	        AccountReadModel model = repository
	            .findById(event.accountId())
	            .orElseGet(AccountReadModel::new);

	        model.setAccountId(event.accountId());
	        model.setBalance(event.balance());
	        model.setLastUpdated(event.occurredAt());
	        model.setLastEventId(event.eventId());

	        repository.save(model);
	    }

}
