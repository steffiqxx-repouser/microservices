package com.example.account.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.account.entity.Account;
import com.example.account.entity.BankTransaction;
import com.example.account.entity.OutboxEvent;
import com.example.account.events.AccountEvent;
import com.example.account.repository.AccountRepository;
import com.example.account.repository.BankTransactionRepository;
import com.example.account.repository.OutboxRepository;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.JacksonException;
import jakarta.transaction.Transactional;
import tools.jackson.databind.ObjectMapper;

@Service
public class AccountCommandService {	

    private final AccountRepository accountRepository;
    private final BankTransactionRepository transactionRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public AccountCommandService(
            AccountRepository accountRepository,
            BankTransactionRepository transactionRepository,
            OutboxRepository outboxRepository,
            ObjectMapper objectMapper) {

        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void withdraw(
            Long accountId,
            BigDecimal amount) throws JacksonException {

        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException(
                "Amount must be positive"
            );
        }

        int updated = accountRepository.debit(
            accountId, amount
        );

        if (updated == 0) {
            throw new IllegalStateException(
                "Account not found or insufficient balance"
            );
        }

        BankTransaction transaction = new BankTransaction();
        transaction.setAccountId(accountId);
        transaction.setAmount(amount);
        transaction.setTransactionType("WITHDRAWAL");
        transaction.setCreatedAt(LocalDateTime.now());

        transactionRepository.save(transaction);

        Account account = accountRepository
            .findById(accountId)
            .orElseThrow();

        AccountEvent event = new AccountEvent(
            UUID.randomUUID(),
            accountId,
            account.getBalance(),
            amount,
            "ACCOUNT_DEBITED",
            LocalDateTime.now()
        );

        OutboxEvent outbox = new OutboxEvent();
        outbox.setId(event.eventId());
        outbox.setEventType(event.eventType());
        outbox.setPayload(
            objectMapper.writeValueAsString(event)
        );
        outbox.setPublished(false);
        outbox.setCreatedAt(LocalDateTime.now());

        outboxRepository.save(outbox);
    }
}
