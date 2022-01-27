package com.example.sendMail.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SMSBody {
    private String mobileNumber;
    private String text;
}
