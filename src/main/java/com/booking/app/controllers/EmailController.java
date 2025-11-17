package com.booking.app.controllers;

import com.booking.app.models.services.UserService;
import com.booking.app.models.services.email.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    private final EmailService emailService;
    private final UserService userService;

    public EmailController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @PostMapping("/sendResetToken")
    public String sendResetToken(@RequestParam String email) {
        try {
            // 1. vytvoriť token
            String token = userService.createPasswordResetToken(email);

            // 2. poslať email
            emailService.sendPasswordResetEmail(email, token);

            return "Reset hesla bol odoslaný na: " + email;

        } catch (MailException e) {
            logger.error("Chyba pri odosielaní emailu: ", e);
            return "Chyba pri odoslaní: " + e.getMessage();
        }
    }

    @PostMapping("/sendReservationEmail")
    public String sendReservationEmail(@RequestParam String customerEmail) {
        try {
            emailService.sendReservationConfirmationEmail(customerEmail);
            return "Potvrdzovací email odoslaný na: " + customerEmail;
        } catch (MailException e) {
            logger.error("Chyba pri odosielaní rezervačného emailu: ", e);
            return "Chyba pri odoslaní: " + e.getMessage();
        }
    }
}
