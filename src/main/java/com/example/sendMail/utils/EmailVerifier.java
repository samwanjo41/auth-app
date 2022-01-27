package com.example.sendMail.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailVerifier {
    public boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}
