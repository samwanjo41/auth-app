package com.example.sendMail.service;

import com.example.sendMail.domain.MyUser;
import com.example.sendMail.domain.Role;
import com.example.sendMail.domain.SMSBody;
import com.example.sendMail.dto.Login;
import com.example.sendMail.dto.UserMail;
import com.example.sendMail.email.EmailSender;
import com.example.sendMail.repository.MyUserRepository;
import com.example.sendMail.token.ConfirmationToken;
import com.example.sendMail.token.ConfirmationTokenService;
import com.example.sendMail.utils.EmailVerifier;
import com.twilio.http.TwilioRestClient;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyUserService implements UserDetailsService {
    @Autowired
    private MyUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ConfirmationTokenService tokenService;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private EmailVerifier emailVerifier;

    @Autowired
    private TwilioMessageService twilioMessageService;
    
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MyUser newUser = userRepository.findByEmail(email).get();
        if (newUser == null) {
            log.error("User does not exist");
            throw new UsernameNotFoundException("user with entered email does not exist");
        }
        return new User(newUser.getUsername(), newUser.getPassword(), newUser.getAuthorities());

    }

    public MyUser saveUser(MyUser newUser) throws MessagingException, TemplateException, IOException {
        //check if email in payload exists
        boolean present = userRepository.findByEmail(newUser.getEmail()).isPresent();
        if(present){
            throw new IllegalStateException("User with entered email exists");
        }

//        TODO: check validity of email
       String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        boolean isValidEmail = emailVerifier.patternMatches(newUser.getEmail(), regexPattern);
        if(!isValidEmail){
            throw new IllegalStateException("Email address entered is not valid");
        }

        //Encrypt password
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

        //Add role "ROLE_USER" to user
        ArrayList<Role> roles = new ArrayList<>();
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roles.add(userRole);
        newUser.setRole(roles);

        //save to database
        userRepository.save(newUser);

        //create confirmation token and send to user
        String token = String.valueOf(UUID.randomUUID());
        ConfirmationToken newToken = new ConfirmationToken();
        newToken.setToken(token);
        newToken.setCreatedAt(LocalDateTime.now());
        newToken.setExpiresAt(LocalDateTime.now().plusDays(3));
        newToken.setMyUser(newUser);

        //save token to database
        tokenService.saveToken(newToken);

        //send email containing token to user
        String link = "http://localhost:8080/api/v1/confirm?token=" + token;
        String template = "" +
                "<a href=\"" + link + "\"><h2>Confirm your email</h2></a>";
        String text = "Confirm your email: "+ link;


        String username = newUser.getFirstName() + " " + newUser.getLastName();

        SMSBody newSmsBody = new SMSBody();
        newSmsBody.setMobileNumber(newUser.getPhoneNumber());
        newSmsBody.setText(text);

        UserMail userMail = new UserMail();
        userMail.setUsername(username);
        userMail.setLink(link);
        userMail.setEmail(newUser.getEmail());

        emailSender.sendEmail(userMail);
        twilioMessageService.sendMessage(newSmsBody);

        return newUser;
    }

    public String canLogin(Login login) {
        MyUser newUser = userRepository.findByEmail(login.getUsername()).get();

        //check to see if user account is enabled
        if(!newUser.getIsEnabled()){
            log.error("User account is disabled");
            return "Account is disabled. Confirm your account first!!!";
        }
        String user = newUser.getFirstName();
        return "Hi" + user + "You have Successfully logged in";
    }

    public void updateEnabled(String email) {
        userRepository.enableUser(email);
    }
}
