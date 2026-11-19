package com.example.account.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "account_read_model")
public class AccountReadModel {
	@Id
    private Long accountId;

    private BigDecimal balance;

    private LocalDateTime lastUpdated;

    private UUID lastEventId;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public UUID getLastEventId() {
		return lastEventId;
	}

	public void setLastEventId(UUID lastEventId) {
		this.lastEventId = lastEventId;
	}


}
