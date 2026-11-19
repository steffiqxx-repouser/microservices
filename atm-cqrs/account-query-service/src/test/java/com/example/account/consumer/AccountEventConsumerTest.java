package com.example.account.consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.account.entity.AccountReadModel;
import com.example.account.events.AccountEvent;
import com.example.account.repository.AccountReadRepository;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class AccountEventConsumerTest {

    @Mock
    private AccountReadRepository repository;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    void consumeCreatesAndSavesNewReadModel() throws JacksonException {
        UUID eventId = UUID.randomUUID();
        LocalDateTime occurredAt = LocalDateTime.of(2026, 11, 19, 16, 43);
        AccountEvent event = new AccountEvent(
            eventId,
            21L,
            new BigDecimal("175.25"),
            new BigDecimal("25.25"),
            "DEPOSIT",
            occurredAt
        );
        String message = "{\"accountId\":21}";

        when(objectMapper.readValue(message, AccountEvent.class)).thenReturn(event);
        when(repository.existsByLastEventId(eventId)).thenReturn(false);
        when(repository.findById(21L)).thenReturn(Optional.empty());

        AccountEventConsumer consumer = new AccountEventConsumer(repository, objectMapper);
        consumer.consume(message);

        verify(repository).save(any(AccountReadModel.class));
    }

    @Test
    void consumeUpdatesExistingReadModel() throws JacksonException {
        UUID eventId = UUID.randomUUID();
        AccountReadModel existing = new AccountReadModel();
        AccountEvent event = new AccountEvent(
            eventId,
            22L,
            new BigDecimal("500.00"),
            new BigDecimal("-100.00"),
            "WITHDRAWAL",
            LocalDateTime.of(2026, 11, 19, 17, 0)
        );
        String message = "{\"accountId\":22}";

        when(objectMapper.readValue(message, AccountEvent.class)).thenReturn(event);
        when(repository.existsByLastEventId(eventId)).thenReturn(false);
        when(repository.findById(22L)).thenReturn(Optional.of(existing));

        AccountEventConsumer consumer = new AccountEventConsumer(repository, objectMapper);
        consumer.consume(message);

        verify(repository).save(existing);
    }

    @Test
    void consumeIgnoresDuplicateEvent() throws JacksonException {
        UUID eventId = UUID.randomUUID();
        AccountEvent event = new AccountEvent(
            eventId,
            23L,
            new BigDecimal("100.00"),
            new BigDecimal("10.00"),
            "DEPOSIT",
            LocalDateTime.of(2026, 11, 19, 17, 30)
        );
        String message = "{\"accountId\":23}";

        when(objectMapper.readValue(message, AccountEvent.class)).thenReturn(event);
        when(repository.existsByLastEventId(eventId)).thenReturn(true);

        AccountEventConsumer consumer = new AccountEventConsumer(repository, objectMapper);
        consumer.consume(message);

        verify(repository, never()).findById(any());
        verify(repository, never()).save(any());
    }
}
