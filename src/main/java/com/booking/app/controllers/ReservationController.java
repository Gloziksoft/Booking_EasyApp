package com.booking.app.controllers;

import com.booking.app.data.entities.OfferEntity;
import com.booking.app.data.entities.UserEntity;
import com.booking.app.models.dto.OfferDTO;
import com.booking.app.models.dto.ReservationDTO;
import com.booking.app.models.dto.mappers.OfferMapper;
import com.booking.app.models.exceptions.ReservationNotFoundException;
import com.booking.app.models.services.OfferService;
import com.booking.app.models.services.ReservationService;
import com.booking.app.models.services.UserService;
import com.booking.app.models.services.email.EmailService;
import jakarta.validation.Valid;
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

    private final ReservationService reservationService;
    private final OfferService offerService;
    private final UserService userService;
    private final OfferMapper offerMapper;
    private final EmailService emailService;

    public ReservationController(ReservationService reservationService,
                                 OfferService offerService,
                                 UserService userService,
                                 OfferMapper offerMapper,
                                 EmailService emailService) {
        this.reservationService = reservationService;
        this.offerService = offerService;
        this.userService = userService;
        this.offerMapper = offerMapper;
        this.emailService = emailService;
    }

    @GetMapping
    public String listReservations(Model model,
                                   @AuthenticationPrincipal User user,
                                   @RequestParam(required = false) String keyword, // Pridaný parameter pre search
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "4") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReservationDTO> reservationPage;

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // Logika vetvenia: Search vs. List
        if (isAdmin) {
            if (keyword != null && !keyword.isBlank()) {
                // Admin hľadá (nová metóda, ktorú pridáme do Service)
                reservationPage = reservationService.search(keyword, pageable);
            } else {
                // Admin pozerá všetko
                reservationPage = reservationService.findAll(pageable);
            }
        } else {
            // Bežný užívateľ pozerá len svoje (tu search zatiaľ neriešime)
            reservationPage = reservationService.findByUserEmail(user.getUsername(), pageable);
        }

        model.addAttribute("reservations", reservationPage.getContent());
        model.addAttribute("currentPage", reservationPage.getNumber());
        model.addAttribute("totalPages", reservationPage.getTotalPages());
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("keyword", keyword); // Dôležité pre frontend (udržanie textu v inpute)

        return "pages/reservations/index";
    }

    @GetMapping("/{id}/reserve")
    public String reserveOffer(@PathVariable Long id,
                               Model model,
                               @AuthenticationPrincipal User user) {
        OfferDTO offer = offerService.findById(id);
        if (offer == null) {
            throw new ReservationNotFoundException("Offer not found with id " + id);
        }

        ReservationDTO reservation = reservationService.prepareReservation(id, user.getUsername());
        model.addAttribute("reservation", reservation);
        model.addAttribute("offer", offer);
        return "pages/reservations/reserve";
    }

    @PostMapping("/{id}/reserve")
    public String createReservation(@ModelAttribute("reservation") ReservationDTO reservationDTO,
                                    @AuthenticationPrincipal User user,
                                    RedirectAttributes redirectAttributes) {

        UserEntity userEntity = userService.findByEmail(user.getUsername())
                .orElseThrow(() -> new ReservationNotFoundException("User not found"));
        OfferEntity offerEntity = offerService.findEntityById(reservationDTO.getOfferId());

        ReservationDTO createdReservation = reservationService.create(reservationDTO, userEntity, offerEntity);

        redirectAttributes.addFlashAttribute("message", "Potvrdenie rezervácie sme poslali na váš email.");
        return "redirect:/reservations/detail/" + createdReservation.getId();
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id,
                           Model model,
                           @AuthenticationPrincipal User user) {

        ReservationDTO reservation = reservationService.findById(id);
        if (reservation == null) {
            throw new ReservationNotFoundException("Reservation not found with id " + id);
        }

        if (!reservationService.edit(reservation, user)) {
            return "redirect:/reservations?error=not-authorized";
        }

        OfferDTO offer = offerService.findById(reservation.getOfferId());
        reservation.setOfferStartDateTime(offer.getStartDateTime());
        reservation.setOfferEndDateTime(offer.getEndDateTime());

        model.addAttribute("reservation", reservation);
        model.addAttribute("offers", offerService.findAll());
        model.addAttribute("offer", offer);

        return "pages/reservations/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("reservation") ReservationDTO reservationDTO,
                         BindingResult bindingResult,
                         Model model,
                         @AuthenticationPrincipal User user) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("offers", offerService.findAll());
            return "pages/reservations/edit";
        }

        reservationService.update(id, reservationDTO, user);
        return "redirect:/reservations";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        ReservationDTO reservation = reservationService.findById(id);
        if (reservation == null) {
            throw new ReservationNotFoundException("Reservation not found with id " + id);
        }
        model.addAttribute("reservation", reservation);
        return "pages/reservations/detail";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         @AuthenticationPrincipal User user) {
        reservationService.delete(id, user);
        return "redirect:/reservations";
    }

//    @ExceptionHandler(NoSuchElementException.class)
//    public String handleNotFound(RedirectAttributes redirectAttributes) {
//        redirectAttributes.addFlashAttribute("error", "Reservation not found.");
//        return "redirect:/reservations";
//    }
}
