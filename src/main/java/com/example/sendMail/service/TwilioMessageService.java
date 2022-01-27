package com.example.sendMail.service;

import com.example.sendMail.domain.SMSBody;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TwilioMessageService {
    private static final Logger logger = LoggerFactory.getLogger(TwilioMessageService.class);
    @Value("${accountSID}")
    private String accountSID;

    @Value("${accountAuthToken}")
    private String accountAuthToken;

    @Value("${twilloSenderNumber}")
    private String twilioSenderNumber;

    public String sendMessage(SMSBody smsBody) {
        try{
            Twilio.init(accountSID, accountAuthToken);

            String smsText = smsBody.getText();
            String mobileNumber = smsBody.getMobileNumber();


            PhoneNumber receiverPhoneNumber = new PhoneNumber(mobileNumber);
            PhoneNumber senderPhoneNumber = new PhoneNumber(twilioSenderNumber);

            MessageCreator creator = Message.creator(receiverPhoneNumber, senderPhoneNumber, smsText);
            Message create = creator.create();

//            BigDecimal billingAmount = create.getPrice();
            Message.Status status = create.getStatus();

            logger.info("Message Send Successfully to the number " + mobileNumber);
//            logger.info("BillingAmount is: " + billingAmount);
            logger.info("Status is: " + status);
            return "Message Send Successfully";

        }catch (Exception e){
            logger.error("Exception in sendMessage Method " + e);
            return "Message Send Fail";
        }
    }
}
