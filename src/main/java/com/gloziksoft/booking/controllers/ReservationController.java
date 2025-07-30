package com.gloziksoft.booking.controllers;

import com.gloziksoft.booking.data.enums.ServiceType;
import com.gloziksoft.booking.models.dto.ReservationDTO;
import com.gloziksoft.booking.models.services.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    /**
     * Lists reservations with pagination.
     * Admin users see all reservations, others see only their own.
     *
     * @param model model to pass data to the view
     * @param user authenticated user
     * @param page current page number (default 0)
     * @param size number of reservations per page (default 4)
     * @return reservations listing page
     */
    @GetMapping
    public String listReservations(
            Model model,
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {

        Pageable pageable = PageRequest.of(page, size);

        // Check if user has admin role
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        Page<ReservationDTO> reservationPage;

        if (isAdmin) {
            reservationPage = reservationService.findAll(pageable);
        } else {
            reservationPage = reservationService.findByUserEmail(user.getUsername(), pageable);
        }

        model.addAttribute("reservations", reservationPage.getContent());
        model.addAttribute("currentPage", reservationPage.getNumber());
        model.addAttribute("totalPages", reservationPage.getTotalPages());

        return "pages/reservations/index";
    }

    /**
     * Shows the form for creating a new reservation.
     *
     * @param model model to pass data to the view
     * @return create reservation page
     */
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("reservation", new ReservationDTO());
        model.addAttribute("serviceTypes", ServiceType.values());
        return "pages/reservations/create";
    }

    /**
     * Handles creation of a new reservation.
     *
     * @param reservationDTO data submitted by user
     * @param user authenticated user
     * @return redirect to reservations listing
     */
    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute("reservation") ReservationDTO reservationDTO,
            BindingResult bindingResult,
            @AuthenticationPrincipal User user,
            Model model) {

        if (bindingResult.hasErrors()) {
            // If validation errors exist, return the form with error messages and service types
            model.addAttribute("serviceTypes", ServiceType.values());
            return "pages/reservations/create"; // Thymeleaf form view
        }

        // Save reservation if validation passes
        reservationService.create(reservationDTO, user.getUsername());

        return "redirect:/reservations";
    }

    /**
     * Shows the form for editing an existing reservation.
     *
     * @param id reservation id
     * @param model model to pass data to the view
     * @return edit reservation page
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("reservation", reservationService.findById(id));
        model.addAttribute("serviceTypes", ServiceType.values());
        return "pages/reservations/edit";
    }

    /**
     * Handles updating an existing reservation.
     *
     * @param id reservation id
     * @param reservationDTO updated reservation data
     * @return redirect to reservations listing
     */
    @PostMapping("/edit/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("reservation") ReservationDTO reservationDTO,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            // Return form with errors and service types if validation fails
            model.addAttribute("serviceTypes", ServiceType.values());
            return "pages/reservations/edit";
        }

        reservationService.update(id, reservationDTO);

        return "redirect:/reservations";
    }

    /**
     * Displays details of a reservation.
     *
     * @param id reservation id
     * @param model model to pass data to the view
     * @return reservation detail page
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("reservation", reservationService.findById(id));
        return "pages/reservations/detail";
    }

    /**
     * Handles deletion of a reservation.
     *
     * @param id reservation id
     * @return redirect to reservations listing
     */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        reservationService.delete(id);
        return "redirect:/reservations";
    }

    /**
     * Handles cases where a reservation is not found.
     *
     * @param redirectAttributes attributes to pass error message on redirect
     * @return redirect to reservations listing with error message
     */
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFound(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Reservation not found.");
        return "redirect:/reservations";
    }
}
