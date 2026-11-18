package com.example.account.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.account.service.AccountCommandService;

@ExtendWith(MockitoExtension.class)
class AccountCommandControllerTest {

    @Mock private AccountCommandService service;

    @Test
    void withdrawReturnsAcceptedResponseAndDelegates() throws Exception {
        AccountCommandController controller =
            new AccountCommandController(service);

        ResponseEntity<String> response =
            controller.withdraw(12L, new BigDecimal("40"));

        verify(service).withdraw(12L, new BigDecimal("40"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Withdrawal accepted", response.getBody());
    }
}
