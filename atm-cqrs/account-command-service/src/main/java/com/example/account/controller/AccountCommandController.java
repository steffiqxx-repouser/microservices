package com.example.account.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;
import com.example.account.service.AccountCommandService;
import tools.jackson.core.JacksonException;

@RestController
@RequestMapping("/api/accounts")
public class AccountCommandController {

    private final AccountCommandService service;

    public AccountCommandController(
            AccountCommandService service) {
        this.service = service;
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<String> withdraw(
            @PathVariable Long id,
            @RequestParam BigDecimal amount)
            throws JacksonException {

        service.withdraw(id, amount);

        return ResponseEntity.ok(
            "Withdrawal accepted"
        );
    }
}
