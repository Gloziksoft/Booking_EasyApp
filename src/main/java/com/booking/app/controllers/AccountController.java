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

import java.util.List;

/**
 * Controller for handling user account actions like login, registration, and profile view.
 */
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

    /**
     * Displays the login page.
     *
     * @return The login page view.
     */
    @GetMapping("/login")
    public String renderLogin() {
        return "pages/account/login";
    }

    /**
     * Displays the registration page with a fresh or existing UserDTO.
     *
     * @param model The model to populate with form data.
     * @return The registration page view.
     */
    @GetMapping("/register")
    public String renderRegister(Model model) {
        if (!model.containsAttribute("userDTO")) {
            model.addAttribute("userDTO", new UserDTO());
        }
        return "pages/account/register";
    }

    /**
     * Handles user registration.
     *
     * @param userDTO            Data submitted by the user from the registration form.
     * @param result             BindingResult to capture validation errors.
     * @param redirectAttributes Redirect attributes for passing data across redirects.
     * @return Redirects back to registration page if there are errors, otherwise to login page.
     */
    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("userDTO") UserDTO userDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        // Handle validation errors
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDTO", result);
            redirectAttributes.addFlashAttribute("userDTO", userDTO);
            return "redirect:/account/register";
        }

        try {
            // Attempt to create a new user (non-admin by default)
            userService.create(userDTO, false);

        } catch (DuplicateEmailException e) {
            // Email already exists
            result.rejectValue("email", "error", "Email už existuje.");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDTO", result);
            redirectAttributes.addFlashAttribute("userDTO", userDTO);
            return "redirect:/account/register";

        } catch (PasswordsDoNotEqualException e) {
            // Password and confirmPassword do not match
            result.rejectValue("password", "error", "Heslá sa nezhodujú.");
            result.rejectValue("confirmPassword", "error", "Heslá sa nezhodujú.");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDTO", result);
            redirectAttributes.addFlashAttribute("userDTO", userDTO);
            return "redirect:/account/register";
        }

        // Success - redirect to login
        redirectAttributes.addFlashAttribute("success", "Registrácia bola úspešná.");
        return "redirect:/account/login";
    }

    /**
     * Displays the profile page for the logged-in user, including their reservations.
     * Admins see all users and all reservations.
     *
     * @param userDetails The currently authenticated user.
     * @param model       Model to pass user and reservation data to the view.
     * @return The profile page view.
     */
    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();

        // Fetch the user entity based on email
        UserEntity user = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Používateľ s e-mailom sa nenašiel: " + email));

        model.addAttribute("user", user);

        // Check if the user has ADMIN role
        boolean isAdmin = user.getRole() == Role.ADMIN;


        // Fetch reservations based on role
        List<ReservationDTO> reservations;
        if (isAdmin) {
            reservations = reservationService.findAll();
        } else {
            reservations = reservationService.findByUserEmail(email);
        }

        model.addAttribute("reservations", reservations);
        model.addAttribute("isAdmin", isAdmin);

        // If admin, show all users
        if (isAdmin) {
            model.addAttribute("allUsers", userRepository.findAll());
        }

        return "pages/account/profile";
    }

    public AccountController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        return "pages/account/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPasswordSubmit(@RequestParam String email, Model model) {
        emailService.sendPasswordResetEmail(email);

        model.addAttribute("message", "Ak účet existuje, poslali sme ti inštrukcie na email." + email);
        return "pages/account/forgot-password";
    }
}
