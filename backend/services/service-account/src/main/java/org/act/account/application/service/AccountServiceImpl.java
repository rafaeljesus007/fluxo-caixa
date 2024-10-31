package org.act.account.application.service;

import org.act.account.application.dto.LaunchDTO;
import org.act.account.domain.entities.Account;
import org.act.account.infrastructure.interfaces.AccountRepositoryInterface;
import org.act.account.application.dto.AccountDTO;
import org.act.account.application.interfaces.AccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountServiceInterface {

    @Autowired
    private AccountRepositoryInterface accountRepository;

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public Optional<AccountDTO> getAccountById(Long id) {
        return accountRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = new Account();
        account.setName(accountDTO.getName());
        return convertToDTO(accountRepository.save(account));
    }

    @Override
    public AccountDTO updateAccount(Long id, AccountDTO accountDTO) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setName(accountDTO.getName());
        return convertToDTO(accountRepository.save(account));
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Double calculateDailyBalance(LocalDate date) {
        // Aqui, você faria a lógica para buscar os lançamentos e calcular o saldo.
        // A implementação real dependerá da forma como você está acessando os lançamentos.

        // Placeholder para lógica de cálculo do saldo.
        return 0.0;
    }

    private AccountDTO convertToDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setName(account.getName());
        dto.setBalance(account.getBalance());
        return dto;
    }

    @Override
    public BigDecimal calculateDailyConsolidatedBalance(Long accountId, LocalDate date) {
       return null;
    }
}

