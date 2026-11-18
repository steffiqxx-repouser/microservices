package com.example.account.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.account.entity.Account;
import com.example.account.entity.BankTransaction;
import com.example.account.entity.OutboxEvent;
import com.example.account.repository.AccountRepository;
import com.example.account.repository.BankTransactionRepository;
import com.example.account.repository.OutboxRepository;

import tools.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class AccountCommandServiceTest {

    @Mock private AccountRepository accountRepository;
    @Mock private BankTransactionRepository transactionRepository;
    @Mock private OutboxRepository outboxRepository;
    @Mock private ObjectMapper objectMapper;

    private AccountCommandService service;

    @BeforeEach
    void setUp() {
        service = new AccountCommandService(
            accountRepository,
            transactionRepository,
            outboxRepository,
            objectMapper
        );
    }

    @Test
    void withdrawCreatesTransactionAndOutboxEvent() throws Exception {
        Long accountId = 42L;
        BigDecimal amount = new BigDecimal("25.50");
        Account account = new Account();
        account.setAccountId(accountId);
        account.setBalance(new BigDecimal("74.50"));

        when(accountRepository.debit(accountId, amount)).thenReturn(1);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(objectMapper.writeValueAsString(any())).thenReturn("{\"event\":\"ACCOUNT_DEBITED\"}");

        service.withdraw(accountId, amount);

        ArgumentCaptor<BankTransaction> transactionCaptor =
            ArgumentCaptor.forClass(BankTransaction.class);
        verify(transactionRepository).save(transactionCaptor.capture());
        BankTransaction transaction = transactionCaptor.getValue();
        org.junit.jupiter.api.Assertions.assertEquals(accountId, transaction.getAccountId());
        org.junit.jupiter.api.Assertions.assertEquals(amount, transaction.getAmount());
        org.junit.jupiter.api.Assertions.assertEquals("WITHDRAWAL", transaction.getTransactionType());
        org.junit.jupiter.api.Assertions.assertNotNull(transaction.getCreatedAt());

        ArgumentCaptor<OutboxEvent> outboxCaptor =
            ArgumentCaptor.forClass(OutboxEvent.class);
        verify(outboxRepository).save(outboxCaptor.capture());
        OutboxEvent outbox = outboxCaptor.getValue();
        org.junit.jupiter.api.Assertions.assertEquals("ACCOUNT_DEBITED", outbox.getEventType());
        org.junit.jupiter.api.Assertions.assertEquals("{\"event\":\"ACCOUNT_DEBITED\"}", outbox.getPayload());
        org.junit.jupiter.api.Assertions.assertFalse(outbox.isPublished());
        org.junit.jupiter.api.Assertions.assertNotNull(outbox.getCreatedAt());
        org.junit.jupiter.api.Assertions.assertNotNull(outbox.getId());
    }

    @Test
    void withdrawRejectsNullAmount() {
        assertThrows(IllegalArgumentException.class,
            () -> service.withdraw(1L, null));
        verify(accountRepository, never()).debit(any(), any());
    }

    @Test
    void withdrawRejectsZeroAmount() {
        assertThrows(IllegalArgumentException.class,
            () -> service.withdraw(1L, BigDecimal.ZERO));
        verify(accountRepository, never()).debit(any(), any());
    }

    @Test
    void withdrawRejectsNegativeAmount() {
        assertThrows(IllegalArgumentException.class,
            () -> service.withdraw(1L, new BigDecimal("-1")));
        verify(accountRepository, never()).debit(any(), any());
    }

    @Test
    void withdrawRejectsMissingOrInsufficientAccount() {
        when(accountRepository.debit(99L, new BigDecimal("10")))
            .thenReturn(0);

        assertThrows(IllegalStateException.class,
            () -> service.withdraw(99L, new BigDecimal("10")));
        verify(transactionRepository, never()).save(any());
        verify(outboxRepository, never()).save(any());
    }

    @Test
    void withdrawFailsWhenAccountDisappearsAfterDebit() {
        Long accountId = 7L;
        BigDecimal amount = new BigDecimal("10");
        when(accountRepository.debit(accountId, amount)).thenReturn(1);
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(java.util.NoSuchElementException.class,
            () -> service.withdraw(accountId, amount));
    }
}
