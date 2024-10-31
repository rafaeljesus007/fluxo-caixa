package org.act.account.application.interfaces;


import org.act.account.application.dto.AccountDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AccountServiceInterface {
    List<AccountDTO> getAllAccounts();
    Optional<AccountDTO> getAccountById(Long id);
    AccountDTO createAccount(AccountDTO accountDTO);
    AccountDTO updateAccount(Long id, AccountDTO accountDTO);
    void deleteAccount(Long id);
    Double calculateDailyBalance(LocalDate date);
    BigDecimal calculateDailyConsolidatedBalance(Long accountId, LocalDate date);
}

