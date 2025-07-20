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

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String renderLogin() {
        return "pages/account/login"; // HTML: templates/pages/account/login.html
    }

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

        redirectAttributes.addFlashAttribute("success", "Registrácia úspešná.");
        return "redirect:/account/login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "pages/dashboard"; // templates/pages/dashboard.html
    }
}
