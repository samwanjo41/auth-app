package com.example.sendMail.repository;

import com.example.sendMail.domain.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE MyUser m "+
            "SET m.isEnabled = TRUE WHERE m.email = ?1")
    int enableUser(String email);
}
