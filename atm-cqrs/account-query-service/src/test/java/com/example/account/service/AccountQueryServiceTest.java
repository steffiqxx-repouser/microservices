package com.example.account.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.account.entity.AccountReadModel;
import com.example.account.repository.AccountReadRepository;

@ExtendWith(MockitoExtension.class)
class AccountQueryServiceTest {

    @Mock
    private AccountReadRepository repository;

    @Test
    void getBalanceReturnsStoredAccount() {
        AccountReadModel model = new AccountReadModel();
        model.setAccountId(42L);
        when(repository.findById(42L)).thenReturn(Optional.of(model));

        AccountQueryService service = new AccountQueryService(repository);

        assertSame(model, service.getBalance(42L));
        verify(repository).findById(42L);
    }

    @Test
    void getBalanceThrowsWhenAccountDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        AccountQueryService service = new AccountQueryService(repository);

        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> service.getBalance(99L)
        );
        org.junit.jupiter.api.Assertions.assertEquals(
            "Account not found",
            exception.getMessage()
        );
        verify(repository).findById(99L);
    }
}
