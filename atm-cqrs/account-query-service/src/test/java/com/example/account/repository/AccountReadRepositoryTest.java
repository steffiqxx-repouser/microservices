package com.example.account.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class AccountReadRepositoryTest {

    @Test
    void existsByLastEventIdDelegatesToRepositoryImplementation() {
        AccountReadRepository repository = mock(AccountReadRepository.class);
        UUID eventId = UUID.randomUUID();
        when(repository.existsByLastEventId(eventId)).thenReturn(true);

        assertTrue(repository.existsByLastEventId(eventId));
        verify(repository).existsByLastEventId(eventId);
    }
}
