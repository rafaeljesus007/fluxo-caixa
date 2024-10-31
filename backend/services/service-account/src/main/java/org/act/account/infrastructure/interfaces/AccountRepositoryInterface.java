package org.act.account.infrastructure.interfaces;

import org.act.account.domain.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepositoryInterface extends JpaRepository<Account, Long> {
}

