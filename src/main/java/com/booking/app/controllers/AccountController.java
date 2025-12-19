package com.booking.app.controllers;

import com.booking.app.data.entities.UserEntity;
import com.booking.app.data.enums.Role;
import com.booking.app.data.repositories.UserRepository;
import com.booking.app.models.dto.ReservationDTO;
import com.booking.app.models.dto.UserDTO;
import com.booking.app.models.exceptions.DuplicateEmailException;
import com.booking.app.models.exceptions.PasswordsDoNotEqualException;
import com.booking.app.models.services.ReservationService;
import com.booking.app.models.services.UserService;
import com.booking.app.models.services.email.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationService reservationService;

    private final EmailService emailService;

    public AccountController(EmailService emailService) {
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
        if (!model.containsAttribute("userDTO")) {
            model.addAttribute("userDTO", new UserDTO());
        }
        return "pages/account/register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("userDTO") UserDTO userDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDTO", result);
            redirectAttributes.addFlashAttribute("userDTO", userDTO);
            return "redirect:/account/register";
        }

        try {
            userService.create(userDTO, false);

        } catch (DuplicateEmailException e) {
            result.rejectValue("email", "error", "Email už existuje.");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDTO", result);
            redirectAttributes.addFlashAttribute("userDTO", userDTO);
            return "redirect:/account/register";

        } catch (PasswordsDoNotEqualException e) {
            result.rejectValue("password", "error", "Heslá sa nezhodujú.");
            result.rejectValue("confirmPassword", "error", "Heslá sa nezhodujú.");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDTO", result);
            redirectAttributes.addFlashAttribute("userDTO", userDTO);
            return "redirect:/account/register";
        }

        redirectAttributes.addFlashAttribute("success", "Registrácia bola úspešná.");
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
    public String forgotPasswordForm() {
        return "pages/account/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPasswordSubmit(@RequestParam String email, Model model) {

        String token = userService.createPasswordResetToken(email);

        // Nezobrazujeme, či účet existuje
        if (token != null) {
            emailService.sendPasswordResetEmail(email, token);
        }

        model.addAttribute("message", "Ak účet existuje, poslali sme inštrukcie na email.");
        return "pages/account/forgot-password";
    }

    // --------------------------------------------------------------------
    // RESET PASSWORD
    // --------------------------------------------------------------------
    @GetMapping("/reset-password")
    public String resetPasswordForm(@RequestParam String token, Model model) {

        Optional<UserEntity> userOpt = userRepository.findByResetToken(token);

        if (userOpt.isEmpty() ||
                userOpt.get().getResetTokenExpiration().isBefore(LocalDateTime.now())) {

            model.addAttribute("error", "Token je neplatný alebo expiroval.");
            return "pages/account/reset-password-error";
        }

        model.addAttribute("token", token);
        return "pages/account/reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam String token,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("token", token);
            model.addAttribute("error", "Heslá sa nezhodujú.");
            return "pages/account/reset-password";
        }

        boolean success = userService.resetPassword(token, password);

        if (!success) {
            model.addAttribute("error", "Token je neplatný alebo expiroval.");
            return "pages/account/reset-password";
        }

        return "redirect:/account/login?resetSuccess";
    }
}
