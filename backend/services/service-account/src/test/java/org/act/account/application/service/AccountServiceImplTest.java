package org.act.account.application.service;

import org.act.account.application.dto.AccountDTO;
import org.act.account.application.dto.LaunchDTO;
import org.act.account.domain.entities.Account;
import org.act.account.infrastructure.repository.AccountRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepositoryImpl accountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccount_ShouldReturnAccountDTO() {
        AccountDTO accountCreateDTO = new AccountDTO();
        AccountDTO accountDTO = new AccountDTO();

        when(accountRepository.save(any())).thenReturn(accountDTO);

        AccountDTO createdAccount = accountService.createAccount(accountCreateDTO);

        assertNotNull(createdAccount);
        assertEquals("Test Account", createdAccount.getName());
        verify(accountRepository, times(1)).save(any());
    }


    @Test
    void deleteAccount_ShouldCallDeleteOnRepository() {
        Long accountId = 1L;

        accountService.deleteAccount(accountId);
        Account account1 = new Account();
        verify(accountRepository, times(1)).delete(account1);
    }


    @Test
    void calculateDailyConsolidatedBalance_ShouldReturnCorrectBalance() {
        Long accountId = 1L;
        LocalDate date = LocalDate.now();
        BigDecimal expectedBalance = BigDecimal.valueOf(100);

        // Simulando o retorno do endpoint de lançamentos
        LaunchDTO[] launches = new LaunchDTO[]{
                new LaunchDTO(),
                new LaunchDTO()
        };


        BigDecimal balance = accountService.calculateDailyConsolidatedBalance(accountId, date);

        assertNotNull(balance);
        assertEquals(expectedBalance, balance);
    }
}

