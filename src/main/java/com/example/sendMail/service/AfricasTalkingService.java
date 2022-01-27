package com.example.sendMail.service;

import com.africastalking.AfricasTalking;
import com.africastalking.*;
import com.africastalking.SmsService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AfricasTalkingService {

    private static final String USERNAME = "sandbox";
    private static final String API_KEY = "7e7afd3086fa2fcfe01787f5a831ba90b6e9e487d1609622fad20f79e087f84b";
    private static Server server;

    public void setup() throws IOException {
        AfricasTalking.initialize(USERNAME, API_KEY);
        AfricasTalking.setLogger(new Logger() {
            @Override
            public void log(String message, Object... args) {
                System.out.println(message);
            }
        });
        server = new Server();
        server.startInsecure();

    }


}