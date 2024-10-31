package org.act.launch.infrastructure.interfaces;


import org.act.launch.domain.entities.Launch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LaunchRepositoryInterface extends JpaRepository<Launch, Long> {
    // Métodos personalizados podem ser definidos aqui, se necessário

    @Query("SELECT l FROM Launch l WHERE l.accountId = :accountId AND l.date BETWEEN :startDate AND :endDate")
    List<Launch> findByAccountIdAndDateBetween(
            @Param("accountId") Long accountId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}

