package com.example.account.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class EntityTest {

    @Test
    void accountGettersAndSetters() {
        Account account = new Account();
        account.setAccountId(10L);
        account.setBalance(new BigDecimal("125.25"));
        account.setAccountHolder("Ada");

        assertEquals(10L, account.getAccountId());
        assertEquals(new BigDecimal("125.25"), account.getBalance());
        assertEquals("Ada", account.getAccountHolder());
    }

    @Test
    void bankTransactionGettersAndSetters() {
        BankTransaction transaction = new BankTransaction();
        LocalDateTime createdAt = LocalDateTime.of(2026, 11, 18, 12, 30);

        transaction.setId(5L);
        transaction.setAccountId(10L);
        transaction.setAmount(new BigDecimal("25"));
        transaction.setTransactionType("WITHDRAWAL");
        transaction.setCreatedAt(createdAt);

        assertEquals(5L, transaction.getId());
        assertEquals(10L, transaction.getAccountId());
        assertEquals(new BigDecimal("25"), transaction.getAmount());
        assertEquals("WITHDRAWAL", transaction.getTransactionType());
        assertEquals(createdAt, transaction.getCreatedAt());
    }

    @Test
    void outboxEventGettersAndSetters() {
        OutboxEvent event = new OutboxEvent();
        UUID id = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.of(2026, 11, 18, 12, 30);

        event.setId(id);
        event.setPayload("{\"accountId\":10}");
        event.setEventType("ACCOUNT_DEBITED");
        event.setPublished(true);
        event.setCreatedAt(createdAt);

        assertEquals(id, event.getId());
        assertEquals("{\"accountId\":10}", event.getPayload());
        assertEquals("ACCOUNT_DEBITED", event.getEventType());
        assertTrue(event.isPublished());
        assertEquals(createdAt, event.getCreatedAt());

        event.setPublished(false);
        assertFalse(event.isPublished());
    }
}
