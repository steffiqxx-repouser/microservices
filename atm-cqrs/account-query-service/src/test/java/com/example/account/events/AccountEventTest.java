package com.example.account.events;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class AccountEventTest {

    @Test
    void recordExposesAllValues() {
        UUID eventId = UUID.randomUUID();
        LocalDateTime occurredAt = LocalDateTime.of(2026, 11, 19, 16, 43);
        AccountEvent event = new AccountEvent(
            eventId,
            15L,
            new BigDecimal("300.00"),
            new BigDecimal("50.00"),
            "DEPOSIT",
            occurredAt
        );

        assertEquals(eventId, event.eventId());
        assertEquals(15L, event.accountId());
        assertEquals(new BigDecimal("300.00"), event.balance());
        assertEquals(new BigDecimal("50.00"), event.amount());
        assertEquals("DEPOSIT", event.eventType());
        assertEquals(occurredAt, event.occurredAt());
    }
}
