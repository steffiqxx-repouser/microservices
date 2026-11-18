package com.example.account.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

class RepositoryContractTest {

    @Test
    void accountRepositoryDebitIsModifyingAndBalanceGuarded() throws Exception {
        Method debit = AccountRepository.class.getMethod(
            "debit", Long.class, java.math.BigDecimal.class);
        assertTrue(debit.isAnnotationPresent(Modifying.class));

        String query = debit.getAnnotation(Query.class).value();
        assertTrue(query.contains("SET a.balance = a.balance - :amount"));
        assertTrue(query.contains("AND a.balance >= :amount"));
    }

    @Test
    void accountRepositoryCreditIsModifying() throws Exception {
        Method credit = AccountRepository.class.getMethod(
            "credit", Long.class, java.math.BigDecimal.class);
        assertTrue(credit.isAnnotationPresent(Modifying.class));

        String query = credit.getAnnotation(Query.class).value();
        assertTrue(query.contains("SET a.balance = a.balance + :amount"));
        assertFalse(query.contains("AND a.balance"));
    }

    @Test
    void outboxRepositoryHasOrderedPendingQueryMethod() {
        assertTrue(
            java.util.Arrays.stream(OutboxRepository.class.getMethods())
                .anyMatch(method ->
                    method.getName().equals(
                        "findTop100ByPublishedFalseOrderByCreatedAtAsc"))
        );
    }
}
