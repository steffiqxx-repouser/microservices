package com.example.account.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.account.entity.Account;
@Repository
public interface AccountRepository
        extends JpaRepository<Account, Long> {

    @Modifying
    @Query("""
        UPDATE Account a
        SET a.balance = a.balance - :amount
        WHERE a.accountId = :accountId
          AND a.balance >= :amount
        """)
    int debit(
        @Param("accountId") Long accountId,
        @Param("amount") BigDecimal amount
    );

    @Modifying
    @Query("""
        UPDATE Account a
        SET a.balance = a.balance + :amount
        WHERE a.accountId = :accountId
        """)
    int credit(
        @Param("accountId") Long accountId,
        @Param("amount") BigDecimal amount
    );
}
