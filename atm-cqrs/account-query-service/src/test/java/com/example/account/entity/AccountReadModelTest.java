package com.example.account.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class AccountReadModelTest {

    @Test
    void gettersAndSettersRoundTripValues() {
        AccountReadModel model = new AccountReadModel();
        LocalDateTime updatedAt = LocalDateTime.of(2026, 11, 19, 16, 43);
        UUID eventId = UUID.randomUUID();

        model.setAccountId(12L);
        model.setBalance(new BigDecimal("125.50"));
        model.setLastUpdated(updatedAt);
        model.setLastEventId(eventId);

        assertEquals(12L, model.getAccountId());
        assertEquals(new BigDecimal("125.50"), model.getBalance());
        assertEquals(updatedAt, model.getLastUpdated());
        assertEquals(eventId, model.getLastEventId());
    }
}
