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
        LocalDateTime occurredAt =
            LocalDateTime.of(2026, 11, 18, 12, 30);
        AccountEvent event = new AccountEvent(
            eventId,
            10L,
            new BigDecimal("75"),
            new BigDecimal("25"),
            "ACCOUNT_DEBITED",
            occurredAt
        );

        assertEquals(eventId, event.eventId());
        assertEquals(10L, event.accountId());
        assertEquals(new BigDecimal("75"), event.balance());
        assertEquals(new BigDecimal("25"), event.amount());
        assertEquals("ACCOUNT_DEBITED", event.eventType());
        assertEquals(occurredAt, event.occurredAt());
    }
}
