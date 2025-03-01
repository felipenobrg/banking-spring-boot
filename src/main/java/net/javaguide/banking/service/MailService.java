package net.javaguide.banking.service;

public interface MailService {

    String sendVerificationEmail(String email);

    String generateVerificationCode();

}
