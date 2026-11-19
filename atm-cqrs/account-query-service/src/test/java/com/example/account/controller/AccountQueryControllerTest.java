package com.example.account.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.account.entity.AccountReadModel;
import com.example.account.service.AccountQueryService;

@ExtendWith(MockitoExtension.class)
class AccountQueryControllerTest {

    @Mock
    private AccountQueryService service;

    @Test
    void getBalanceReturnsOkResponseWithAccount() {
        AccountReadModel model = new AccountReadModel();
        model.setAccountId(7L);
        when(service.getBalance(7L)).thenReturn(model);
        AccountQueryController controller = new AccountQueryController(service);

        ResponseEntity<AccountReadModel> response = controller.getBalance(7L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(model, response.getBody());
        verify(service).getBalance(7L);
    }
}
