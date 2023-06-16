package com.krglow.booking.app.service.email;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.krglow.booking.app.service.mail.EmailServiceImpl;


class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        MockitoAnnotations.openMocks(this);
        emailService = new EmailServiceImpl(javaMailSender);

        Field baseUrlField = EmailServiceImpl.class.getDeclaredField("baseUrl");
        baseUrlField.setAccessible(true);
        baseUrlField.set(emailService, "http://test.com");
    }

    @Test
    void sendConfirmationEmail_SuccessfullySendsEmail() {
        String to = "test@example.com";
        String confirmationToken = "abcd1234";
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject("Potwierdzenie rezerwacji");
        mailMessage.setText("Aby potwierdzić rezerwację, kliknij w link: http://test.com/reservations/confirm?token=" + confirmationToken);

        emailService.sendConfirmationEmail(to, confirmationToken);

        verify(javaMailSender, times(1)).send(mailMessage);
    }

    @Test
    void sendConfirmationEmail_FailedToSendEmail_LogsError() {
        String to = "test@example.com";
        String confirmationToken = "abcd1234";
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject("Potwierdzenie rezerwacji");
        mailMessage.setText("Aby potwierdzić rezerwację, kliknij w link: http://test.com/reservations/confirm?token=" + confirmationToken);

        doThrow(new RuntimeException("Failed to send email")).when(javaMailSender).send(mailMessage);

        emailService.sendConfirmationEmail(to, confirmationToken);

        verify(javaMailSender, times(1)).send(mailMessage);
    }

}
