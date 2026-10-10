package com.booking.app.models.services.email;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.base-url}")       // 🔥 sem sa vloží URL z properties
    private String baseUrl;

    @Value("${spring.mail.from}")
    private String mailFrom;

    @Value("${app.mail.bcc}")
    private String bccEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(String customerEmail, String token) {
        try {
            // 🔥 automaticky: localhost:8080 LOKÁLNE, domena PRODUKČNE
            String resetLink = baseUrl + "/account/reset-password?token=" + token;

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setFrom(mailFrom);
            helper.setTo(customerEmail);
            helper.setBcc(bccEmail);
            helper.setSubject("Obnova hesla");

            String text = """
                Dobrý deň,

                Požiadali ste o obnovu hesla.
                Kliknite na nasledujúci odkaz:

                %s

                Odkaz je platný 15 minút.

                Ak ste o zmenu hesla nežiadali, ignorujte tento email.

                S pozdravom,
                BookingApp tím
                """.formatted(resetLink);

            helper.setText(text, false); // plain text

            mailSender.send(message);

        } catch (Exception e) {
            System.err.println("Email sa nepodarilo odoslať: " + e.getMessage());
        }
    }

    public void sendReservationConfirmationEmail(String customerEmail) {
        if (customerEmail == null || customerEmail.isBlank()) {
            System.err.println("Nepodarilo sa odoslať email: customerEmail je null alebo prázdny");
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(customerEmail);
        message.setCc(adminEmail);
        message.setSubject("Potvrdenie rezervácie");
        message.setText(
                "Dobrý deň,\n\n" +
                        "Ďakujeme za vašu rezerváciu. Čoskoro vás budeme kontaktovať s ďalšími informáciami.\n\n" +
                        "S pozdravom,\nBookingApp tím"
        );

        try {
            mailSender.send(message);
            System.out.println("Email úspešne odoslaný na " + customerEmail + " (cc: " + adminEmail + ")");
        } catch (Exception e) {
            System.err.println("Nepodarilo sa odoslať email: " + e.getMessage());
        }
    }
}
