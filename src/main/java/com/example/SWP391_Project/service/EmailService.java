package com.example.SWP391_Project.service;

public interface EmailService {
    void sendSimpleEmail(String toEmail, String body, String subject);
    void sendVerificationEmail(String toEmail, String verificationCode);
}
