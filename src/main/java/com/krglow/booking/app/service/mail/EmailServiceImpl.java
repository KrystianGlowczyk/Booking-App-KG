package com.krglow.booking.app.service.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


/**
 * Implementation of the EmailService.
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${app.baseUrl}")
    private String baseUrl;

    /**
     * Sends a confirmation email.
     *
     * @param to the email address to send to
     * @param confirmationToken the confirmation token
     */
    @Override
    public void sendConfirmationEmail(String to, String confirmationToken) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject("Potwierdzenie rezerwacji");
        mailMessage.setText("Aby potwierdzić rezerwację, kliknij w link: " + baseUrl + "/reservations/confirm?token=" + confirmationToken);

        try {
            javaMailSender.send(mailMessage);
            log.info("Confirmation email sent to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send confirmation email to: {}", to, e);
        }

    }

}
