package com.example.sendMail.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE ConfirmationToken c " +
            "SET c.confirmedAt = ?2 WHERE c.token = ?1")
    int updateConfirmedAt(String token, LocalDateTime confirmedAt);

    ConfirmationToken findByToken(String token);
}
