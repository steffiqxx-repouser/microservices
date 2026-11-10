package com.example.account.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AccountEvent(

	    UUID eventId,

	    Long accountId,

	    BigDecimal balance,

	    BigDecimal amount,

	    String eventType,

	    LocalDateTime occurredAt

	) {}
