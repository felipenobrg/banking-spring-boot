package net.javaguide.banking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import net.javaguide.banking.service.MailService;

import java.util.Random;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendVerificationEmail(String email) {
        String verificationCode = generateVerificationCode();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Email Verification Code");
        message.setText("Your verification code is: " + verificationCode);

        mailSender.send(message);
        return verificationCode;
    }

    public String generateVerificationCode() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

}
