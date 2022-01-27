package com.example.sendMail.service;

import com.example.sendMail.domain.MyUser;
import com.example.sendMail.dto.Login;
import com.example.sendMail.repository.MyUserRepository;
import com.example.sendMail.token.ConfirmationTokenService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class RegisterService {
    @Autowired
    private MyUserService userService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    public MyUser register(MyUser user) throws MessagingException, TemplateException, IOException {
        MyUser newUser = new MyUser();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setPhoneNumber(user.getPhoneNumber());

        return userService.saveUser(newUser);
    }

    public String login(Login login) {
        return userService.canLogin(login);
    }

    public void confirm(String token) {
        //check if token exists
        boolean exists = confirmationTokenService.doesTokenExist(token);
        if(!exists){
            throw new IllegalStateException("Token does not exist");
        }
        //check if token is expired
        boolean isexpired = confirmationTokenService.isExpired(token);
        if(isexpired){
            throw new IllegalStateException("Token is expired");
        }
        //check if token is confirmed
        boolean isconfirmed = confirmationTokenService.isExpired(token);
        if(isconfirmed){
            throw new IllegalStateException("Token is expired");
        }
        //confirm token and update database
        confirmationTokenService.setConfirmed(token);
        //set user.enabled to true
        //Get the user associated with this token
        MyUser user = confirmationTokenService.getUser(token);
        userService.updateEnabled(user.getEmail());

    }
}
