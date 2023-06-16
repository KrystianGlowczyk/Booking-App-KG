package com.krglow.booking.app.service.mail;

public interface EmailService {

    void sendConfirmationEmail(String to, String confirmationToken);

}
