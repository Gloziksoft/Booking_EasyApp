package com.booking.app.controllers;

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

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendPasswordEmail")
    public String sendCustomerEmail(@RequestParam String customerEmail) {
        try {
            emailService.sendPasswordResetEmail(customerEmail);
            return "Email odoslaný na: " + customerEmail;
        } catch (MailException e) {
            logger.error("Chyba pri odosielaní emailu: ", e);
            return "Chyba pri odosielaní: " + e.getMessage();
        }
    }

    @PostMapping("/sendReservationEmail")
    public String sendReservationEmail(@RequestParam String customerEmail) {
        try {
            emailService.sendReservationConfirmationEmail(customerEmail);
            return "Potvrdzovací email odoslaný na: " + customerEmail;
        } catch (MailException e) {
            logger.error("Chyba pri odosielaní rezervačného emailu: ", e);
            return "Chyba pri odosielaní: " + e.getMessage();
        }
    }
}
