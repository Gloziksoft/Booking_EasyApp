package com.booking.app.controllers;

import com.booking.app.data.entities.UserEntity;
import com.booking.app.data.enums.Role;
import com.booking.app.data.repositories.UserRepository;
import com.booking.app.models.dto.*;
import com.booking.app.models.exceptions.DuplicateEmailException;
import com.booking.app.models.exceptions.PasswordsDoNotEqualException;
import com.booking.app.models.services.ReservationService;
import com.booking.app.models.services.UserService;
import com.booking.app.models.services.email.EmailService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ReservationService reservationService;
    private final EmailService emailService;

    public AccountController(
            UserService userService,
            UserRepository userRepository,
            ReservationService reservationService,
            EmailService emailService
    ) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.reservationService = reservationService;
        this.emailService = emailService;
    }

    // --------------------------------------------------------------------
    // LOGIN
    // --------------------------------------------------------------------
    @GetMapping("/login")
    public String renderLogin() {
        return "pages/account/login";
    }

    // --------------------------------------------------------------------
    // REGISTER
    // --------------------------------------------------------------------
    @GetMapping("/register")
    public String renderRegister(Model model) {
        if (!model.containsAttribute("registrationDTO")) {
            model.addAttribute("registrationDTO", new RegistrationDTO());
        }
        return "pages/account/register";
    }


    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registrationDTO") RegistrationDTO registrationDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.registrationDTO",
                    result
            );
            redirectAttributes.addFlashAttribute("registrationDTO", registrationDTO);
            return "redirect:/account/register";
        }

        try {
            UserEntity user = userService.create(registrationDTO, false);

            String token = userService.createEmailVerificationToken(user.getId());

            emailService.sendEmailVerificationEmail(user.getEmail(), token);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Registrácia bola vytvorená. Skontrolujte svoj e-mail a kliknite na overovací odkaz. Odkaz je platný 24 hodín."
            );

            return "redirect:/account/login";

        } catch (DuplicateEmailException e) {
            result.rejectValue("email", "error", "Email už existuje.");

            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.registrationDTO",
                    result
            );
            redirectAttributes.addFlashAttribute("registrationDTO", registrationDTO);
            return "redirect:/account/register";

        } catch (PasswordsDoNotEqualException e) {
            result.rejectValue("password", "error", "Heslá sa nezhodujú.");
            result.rejectValue("confirmPassword", "error", "Heslá sa nezhodujú.");

            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.registrationDTO",
                    result
            );
            redirectAttributes.addFlashAttribute("registrationDTO", registrationDTO);
            return "redirect:/account/register";

        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Registrácia bola uložená, ale overovací e-mail sa nepodarilo odoslať. Kontaktujte podporu."
            );

            return "redirect:/account/login";
        }
    }

    @GetMapping("/verify-email")
    public String verifyEmail(
            @RequestParam(required = false) String token,
            RedirectAttributes redirectAttributes
    ) {
        boolean verified = userService.verifyEmail(token);

        if (verified) {
            redirectAttributes.addFlashAttribute(
                    "success",
                    "E-mail bol úspešne overený. Teraz sa môžete prihlásiť."
            );
        } else {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Overovací odkaz je neplatný alebo expiroval."
            );
        }

        return "redirect:/account/login";
    }

    // --------------------------------------------------------------------
    // PROFILE
    // --------------------------------------------------------------------
    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        String email = userDetails.getUsername();

        UserEntity user = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Používateľ sa nenašiel: " + email));

        model.addAttribute("user", user);
        boolean isAdmin = (user.getRole() == Role.ADMIN);

        List<ReservationDTO> reservations;

        if (isAdmin) {
            reservations = reservationService.findAll();
            model.addAttribute("allUsers", userRepository.findAll());
        } else {
            reservations = reservationService.findByUserEmail(email);
        }

        model.addAttribute("reservations", reservations);
        model.addAttribute("isAdmin", isAdmin);

        return "pages/account/profile";
    }

    // --------------------------------------------------------------------
    // FORGOT PASSWORD – zadanie emailu
    // --------------------------------------------------------------------
    @GetMapping("/forgot-password")
    public String forgotPasswordForm(Model model) {
        if (!model.containsAttribute("forgotPasswordDTO")) {
            model.addAttribute("forgotPasswordDTO", new ForgotPasswordDTO());
        }

        return "pages/account/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPasswordSubmit(
            @Valid @ModelAttribute("forgotPasswordDTO") ForgotPasswordDTO forgotPasswordDTO,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "pages/account/forgot-password";
        }

        String email = forgotPasswordDTO.getEmail();

        String token = userService.createPasswordResetToken(email);
        if (token != null) {
            emailService.sendPasswordResetEmail(email, token);
        }

        model.addAttribute("message",
                "Ak účet existuje, poslali sme inštrukcie na email.");

        return "pages/account/forgot-password";
    }

    // --------------------------------------------------------------------
    // RESET PASSWORD
    // --------------------------------------------------------------------
    @GetMapping("/reset-password")
    public String resetPasswordForm(
            @RequestParam String token,
            Model model
    ) {
        Optional<UserEntity> userOpt =
                userRepository.findByResetToken(token);

        if (userOpt.isEmpty()
                || userOpt.get().getResetTokenExpiration() == null
                || !userOpt.get().getResetTokenExpiration()
                .isAfter(LocalDateTime.now())) {

            model.addAttribute(
                    "error",
                    "Token je neplatný alebo expiroval."
            );
            return "pages/account/reset-password-error";
        }

        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTO();
        resetPasswordDTO.setToken(token);

        model.addAttribute("resetPasswordDTO", resetPasswordDTO);

        return "pages/account/reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @Valid @ModelAttribute("resetPasswordDTO") ResetPasswordDTO resetPasswordDTO,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "pages/account/reset-password";
        }

        if (!resetPasswordDTO.getPassword().equals(resetPasswordDTO.getConfirmPassword())) {
            result.rejectValue(
                    "confirmPassword",
                    "error",
                    "Heslá sa nezhodujú."
            );

            return "pages/account/reset-password";
        }

        boolean success = userService.resetPassword(
                resetPasswordDTO.getToken(),
                resetPasswordDTO.getPassword()
        );

        if (!success) {
            model.addAttribute("error", "Token je neplatný alebo expiroval.");
            return "pages/account/reset-password";
        }

        return "redirect:/account/login?resetSuccess";
    }
}
