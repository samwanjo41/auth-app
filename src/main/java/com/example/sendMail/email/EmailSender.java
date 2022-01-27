package com.example.sendMail.email;

import com.example.sendMail.dto.UserMail;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class EmailSender {

    private final JavaMailSender mailSender;
    private Configuration configuration;


    public void sendEmail(UserMail user) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Confirm Your Email");
        helper.setTo(user.getEmail());
        helper.setFrom("samwanjo41@gmail.com");
        helper.setBcc("samuel.wanjohi@tracom.co.ke");
        String emailContent = getEmailContent(user);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }

    String getEmailContent(UserMail user) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        configuration.getTemplate("email2.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }


}
