package com.gloziksoft.booking.controllers;

import com.gloziksoft.booking.models.dto.UserDTO;
import com.gloziksoft.booking.models.exceptions.DuplicateEmailException;
import com.gloziksoft.booking.models.exceptions.PasswordsDoNotEqualException;
import com.gloziksoft.booking.models.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for handling user account actions like login and registration.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

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
     * Displays the registration page.
     *
     * @param model The model to populate form data.
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
     * @param userDTO            The user registration data.
     * @param result             Binding result for validation errors.
     * @param redirectAttributes Redirect attributes to pass data between redirects.
     * @return Redirect to registration or login page based on result.
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
            // Attempt to create a new user
            userService.create(userDTO, false);

        } catch (DuplicateEmailException e) {
            // Handle duplicate email error
            result.rejectValue("email", "error", "Email už existuje.");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDTO", result);
            redirectAttributes.addFlashAttribute("userDTO", userDTO);
            return "redirect:/account/register";

        } catch (PasswordsDoNotEqualException e) {
            // Handle passwords mismatch error
            result.rejectValue("password", "error", "Heslá sa nezhodujú.");
            result.rejectValue("confirmPassword", "error", "Heslá sa nezhodujú.");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDTO", result);
            redirectAttributes.addFlashAttribute("userDTO", userDTO);
            return "redirect:/account/register";
        }

        // Registration successful
        redirectAttributes.addFlashAttribute("success", "Registrácia bola úspešná.");
        return "redirect:/account/login";
    }
}
