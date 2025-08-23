package com.booking.app.models.services.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.admin.email}")
    private String adminEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends a password reset email to the customer.
     *
     * @param customerEmail Email of the customer
     */
    public void sendPasswordResetEmail(String customerEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("test@gloziksoft.sk");
        message.setTo(customerEmail);
        message.setBcc("peto7724@gmail.com");
        message.setSubject("Obnova hesla");
        message.setText("Dobrý deň,\n\nNa požiadanie sme pripravili obnovenie vášho hesla. " +
                "Kliknite na odkaz nižšie alebo postupujte podľa inštrukcií v emaili.\n\n" +
                "Ďakujeme,\nBookingApp tím");

        mailSender.send(message);
    }

    /**
     * Sends a reservation confirmation email to the customer.
     *
     * @param customerEmail Email of the customer
     */
    /**
     * Rezervačné potvrdenie pre zákazníka + kópia adminovi.
     */
    public void sendReservationConfirmationEmail(String customerEmail) {
        if (customerEmail == null || customerEmail.isBlank()) {
            System.err.println("Nepodarilo sa odoslať email: customerEmail je null alebo prázdny");
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("test@gloziksoft.sk");
        message.setTo(customerEmail);
        message.setCc(adminEmail); // automaticky sa pošle aj adminovi
        message.setSubject("Potvrdenie rezervácie");
        message.setText(
                "Dobrý deň,\n\n" +
                        "Ďakujeme za vašu rezerváciu. Čoskoro vás budeme kontaktovať s ďalšími informáciami.\n\n" +
                        "S pozdravom,\n" +
                        "BookingApp tím"
        );

        try {
            mailSender.send(message);
            System.out.println("Email úspešne odoslaný na " + customerEmail + " (cc: " + adminEmail + ")");
        } catch (Exception e) {
            System.err.println("Nepodarilo sa odoslať email: " + e.getMessage());
        }
    }
}
