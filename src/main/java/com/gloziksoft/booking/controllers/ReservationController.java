package com.gloziksoft.booking.controllers;

import com.gloziksoft.booking.data.entities.OfferEntity;
import com.gloziksoft.booking.data.entities.UserEntity;
import com.gloziksoft.booking.models.dto.ReservationDTO;
import com.gloziksoft.booking.models.services.OfferService;
import com.gloziksoft.booking.models.services.ReservationService;
import com.gloziksoft.booking.models.services.UserService;
import com.gloziksoft.booking.models.dto.mappers.OfferMapper;
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

import jakarta.validation.Valid;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private OfferService offerService;

    @Autowired
    private UserService userService;

    @Autowired
    private OfferMapper offerMapper;

    // --- LIST ---
    @GetMapping
    public String listReservations(Model model,
                                   @AuthenticationPrincipal User user,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "4") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReservationDTO> reservationPage;

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            reservationPage = reservationService.findAll(pageable);
        } else {
            reservationPage = reservationService.findByUserEmail(user.getUsername(), pageable);
        }

        model.addAttribute("reservations", reservationPage.getContent());
        model.addAttribute("currentPage", reservationPage.getNumber());
        model.addAttribute("totalPages", reservationPage.getTotalPages());
        model.addAttribute("isAdmin", isAdmin);

        return "pages/reservations/index";
    }

    // --- CREATE FROM OFFER (POST) ---
    @PostMapping
    public String createReservation(@Valid @ModelAttribute("reservation") ReservationDTO reservationDTO,
                                    BindingResult bindingResult,
                                    Model model,
                                    @AuthenticationPrincipal User user) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("offer", offerService.findById(reservationDTO.getOfferId()));
            return "pages/offers/reserve";
        }

        UserEntity userEntity = userService.findByEmail(user.getUsername())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        OfferEntity offerEntity = offerService.findEntityById(reservationDTO.getOfferId());

        reservationDTO.setTitle(offerEntity.getTitle());
        reservationDTO.setDescription(offerEntity.getDescription());
        reservationDTO.setServiceType(offerEntity.getServiceType());


        reservationService.create(reservationDTO, userEntity, offerEntity);
        return "redirect:/offers";
    }

    // --- EDIT FORM ---
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id,
                           Model model,
                           @AuthenticationPrincipal User user) {

        ReservationDTO reservation = reservationService.findById(id);
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !reservation.getUserEmail().equals(user.getUsername())) {
            return "redirect:/reservations?error=not-authorized";
        }

        model.addAttribute("reservation", reservation);
        model.addAttribute("offers", offerService.findAll());
        model.addAttribute("offer", offerService.findById(reservation.getOfferId()));
        return "pages/reservations/edit";
    }

    // --- UPDATE ---
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("reservation") ReservationDTO reservationDTO,
                         BindingResult bindingResult,
                         Model model,
                         @AuthenticationPrincipal User user) {

        // Ak sú chyby vo validácii, vrátime edit stránku
        if (bindingResult.hasErrors()) {
            model.addAttribute("offers", offerService.findAll());
            return "pages/reservations/edit";
        }

        // --- Ošetrenie vlastníctva ---
        ReservationDTO existing = reservationService.findById(id);
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !existing.getUserEmail().equals(user.getUsername())) {
            // Ak používateľ nie je admin a rezervácia nie je jeho, blokujeme
            return "redirect:/reservations?error=not-authorized";
        }

        // Update cez service
        reservationService.update(id, reservationDTO);

        return "redirect:/reservations";
    }

    // --- DETAIL ---
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("reservation", reservationService.findById(id));
        return "pages/reservations/detail";
    }

    // --- DELETE ---
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         @AuthenticationPrincipal User user) {

        ReservationDTO reservation = reservationService.findById(id);
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !reservation.getUserEmail().equals(user.getUsername())) {
            return "redirect:/reservations?error=not-authorized";
        }

        reservationService.delete(id);
        return "redirect:/reservations";
    }

    // --- EXCEPTION HANDLER ---
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFound(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Reservation not found.");
        return "redirect:/reservations";
    }
}
