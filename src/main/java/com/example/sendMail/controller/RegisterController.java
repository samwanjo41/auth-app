package com.example.sendMail.controller;

import com.africastalking.AfricasTalking;
import com.africastalking.Server;
import com.africastalking.SmsService;
import com.example.sendMail.domain.MyUser;
import com.example.sendMail.domain.SMSBody;
import com.example.sendMail.dto.Login;
import com.example.sendMail.service.RegisterService;
import com.example.sendMail.service.TwilioMessageService;
import freemarker.template.TemplateException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("api/v1")
public class RegisterController {

    private final RegisterService registerService;
    private final TwilioMessageService twilioMessageService;

    public RegisterController(RegisterService registerService, TwilioMessageService twilioMessageService) {
        this.registerService = registerService;
        this.twilioMessageService = twilioMessageService;
    }

    private static final String USERNAME = "sandbox";
    private static final String API_KEY = "Your aficasTalking api key";

    @GetMapping("/home")
    public String home() {
        return "hello world";
    }


    @PostMapping("/register")
    public ResponseEntity<MyUser> registerUser(@RequestBody MyUser user) throws MessagingException, TemplateException, IOException {
        return ResponseEntity.ok(registerService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Login login) {
        return ResponseEntity.ok(registerService.login(login));
    }

    //    @Cacheable(value = "login")
    @GetMapping("/confirm")
    public String confirmRegistration(@RequestParam("token") String token) {
        registerService.confirm(token);
        return "confirmed";
    }

    @PostMapping("/sendSMS")
    public String sendSMS(@RequestBody SMSBody smsBody) {
        return twilioMessageService.sendMessage(smsBody);
    }

    @PostMapping("/africasTalkin/sms")
    public String sendMess() throws IOException {
        AfricasTalking.initialize(USERNAME, API_KEY);
        AfricasTalking.setLogger((message, args) -> System.out.println(message));
        Server server = new Server();
        server.startInsecure();

        SmsService sms = AfricasTalking.getService(AfricasTalking.SERVICE_SMS);
        sms.send("Welcome to Awesome Company", "samwanjo41", new String[]{"+254716217949"}, false);
        return "SMS sent successfully";
    }

}
