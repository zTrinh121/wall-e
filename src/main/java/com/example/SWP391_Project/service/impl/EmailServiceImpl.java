package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendSimpleEmail(String toEmail, String body, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        System.out.println("Mail sent successfully...");
    }

    @Override
    public void sendVerificationEmail(String toEmail, String verificationCode) {
        String subject = "Verify Email";
        String body = "This is your verification code: " + verificationCode;
        sendSimpleEmail(toEmail, body, subject);
    }
}
