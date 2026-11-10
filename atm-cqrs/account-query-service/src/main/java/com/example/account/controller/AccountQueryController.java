package com.example.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.account.entity.AccountReadModel;
import com.example.account.service.AccountQueryService;


@RestController
@RequestMapping("/api/accounts")
public class AccountQueryController {
	 private final AccountQueryService service;

	    public AccountQueryController(
	            AccountQueryService service) {
	        this.service = service;
	    }

	    @GetMapping("/{id}/balance")
	    public ResponseEntity<AccountReadModel> getBalance(
	            @PathVariable Long id) {

	        return ResponseEntity.ok(
	            service.getBalance(id)
	        );
	    }

}
