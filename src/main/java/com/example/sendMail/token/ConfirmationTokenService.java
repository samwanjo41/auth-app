package com.example.sendMail.token;

import com.example.sendMail.domain.MyUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfirmationTokenService {
    @Autowired
    private ConfirmationTokenRepository tokenRepository;

    public ConfirmationToken saveToken(ConfirmationToken token) {
        tokenRepository.save(token);
        return token;
    }

    public MyUser getUser(String token){
        ConfirmationToken myToken = tokenRepository.findByToken(token);
        MyUser user = myToken.getMyUser();
        return user;
    }

    public Boolean doesTokenExist(String token) {
        boolean exists = false;
        ConfirmationToken myToken = tokenRepository.findByToken(token);
        if (myToken != null) {
            exists = true;
        }
        return exists;
    }

    public Boolean isExpired(String token) {
        boolean expired = false;
        ConfirmationToken myToken = tokenRepository.findByToken(token);
        if (myToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            expired = true;
            throw new IllegalStateException("Token has expired");
        }
        return expired;
    }

    public Boolean isConfirmed(String token) {
        boolean confirmed = false;
        ConfirmationToken myToken = tokenRepository.findByToken(token);
        if (myToken.getConfirmedAt() != null) {
            confirmed = true;
            throw new IllegalStateException("Token has been confirmed");
        }
        return confirmed;
    }

    public void setConfirmed(String token){
        tokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
