package com.example.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.account.entity.BankTransaction;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {

}
