package com.example.account.service;

import com.example.account.entity.AccountReadModel;
import com.example.account.repository.AccountReadRepository;

public class AccountQueryService {
	  private final AccountReadRepository repository;

	    public AccountQueryService(
	            AccountReadRepository repository) {
	        this.repository = repository;
	    }

	    public AccountReadModel getBalance(Long accountId) {

	        return repository.findById(accountId)
	            .orElseThrow(() ->
	                new RuntimeException("Account not found")
	            );
	    }

}
