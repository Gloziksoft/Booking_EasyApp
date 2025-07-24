package com.gloziksoft.booking.controllers;

import com.gloziksoft.booking.models.dto.ReservationDTO;
import com.gloziksoft.booking.models.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public String listReservations(
            Model model,
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {

        Pageable pageable = PageRequest.of(page, size);

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


    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("reservation", new ReservationDTO());
        return "pages/reservations/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute ReservationDTO reservationDTO, @AuthenticationPrincipal User user) {
        reservationService.create(reservationDTO, user.getUsername());
        return "redirect:/reservations";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("reservation", reservationService.findById(id));
        return "pages/reservations/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute ReservationDTO reservationDTO) {
        reservationService.update(id, reservationDTO);
        return "redirect:/reservations";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("reservation", reservationService.findById(id));
        return "pages/reservations/detail";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        reservationService.delete(id);
        return "redirect:/reservations";
    }
}
