package com.gloziksoft.booking.controllers;

import com.gloziksoft.booking.data.enums.ServiceType;
import com.gloziksoft.booking.models.dto.OfferDTO;
import com.gloziksoft.booking.models.dto.ReservationDTO;
import com.gloziksoft.booking.models.services.OfferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @GetMapping
    public String listOffers(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "12") int size,
                             @RequestParam(required = false) ServiceType serviceType) {

        Page<OfferDTO> offerPage = (serviceType != null)
                ? offerService.findAllByServiceType(serviceType, PageRequest.of(page, size))
                : offerService.findAll(PageRequest.of(page, size));

        model.addAttribute("offers", offerPage.getContent());
        model.addAttribute("currentPage", offerPage.getNumber());
        model.addAttribute("totalPages", offerPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("serviceType", serviceType);

        return "pages/offers/index";
    }

    @GetMapping("/{id}")
    public String offerDetail(@PathVariable Long id, Model model) {
        OfferDTO offer = offerService.findById(id);
        if (offer == null) {
            return "redirect:/offers";
        }

        ReservationDTO reservation = new ReservationDTO();
        reservation.setOfferId(id);
        reservation.setTitle(offer.getTitle());
        reservation.setDescription(offer.getDescription());
        reservation.setServiceType(offer.getServiceType());

        model.addAttribute("offer", offer);
        return "pages/offers/detail";
    }

    @GetMapping("/{id}/reserve")
    public String reserveOffer(@PathVariable Long id, Model model) {
        OfferDTO offer = offerService.findById(id);
        if (offer == null) {
            return "redirect:/offers";
        }

        ReservationDTO reservation = new ReservationDTO();
        reservation.setOfferId(id);
        reservation.setTitle(offer.getTitle());
        reservation.setDescription(offer.getDescription());
        reservation.setServiceType(offer.getServiceType());
        reservation.setStartDateTime(offer.getStartDateTime());
        reservation.setEndDateTime(offer.getEndDateTime());

        model.addAttribute("offer", offer);
        model.addAttribute("reservation", reservation);

        return "pages/offers/reserve";
    }

    @GetMapping("/create")
    public String createOfferForm(Model model) {
        model.addAttribute("offer", new OfferDTO());
        model.addAttribute("serviceTypes", ServiceType.values());
        return "pages/offers/create";
    }

    @PostMapping("/create")
    public String createOfferSubmit(@ModelAttribute("offer") OfferDTO offerDTO) {
        offerService.create(offerDTO);
        return "redirect:/offers";
    }

    @GetMapping("/{id}/edit")
    public String editOfferForm(@PathVariable Long id, Model model) {
        OfferDTO offer = offerService.findById(id);
        if (offer == null) {
            return "redirect:/offers";
        }

        // Ak sú dátumy null, nastavíme predvolené hodnoty
        if (offer.getStartDateTime() == null) {
            offer.setStartDateTime(LocalDateTime.now());
        }
        if (offer.getEndDateTime() == null) {
            offer.setEndDateTime(LocalDateTime.now().plusHours(1));
        }

        model.addAttribute("offer", offer);
        model.addAttribute("serviceTypes", ServiceType.values());
        return "pages/offers/edit";
    }


    @PostMapping("/{id}/edit")
    public String editOfferSubmit(@PathVariable Long id,
                                  @Valid @ModelAttribute("offer") OfferDTO offerDTO,
                                  BindingResult bindingResult,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("serviceTypes", ServiceType.values());
            return "pages/offers/edit";
        }

        offerDTO.setId(id);
        offerService.update(id, offerDTO);
        return "redirect:/offers/" + id;
    }


    @PostMapping("/{id}/delete")
    public String deleteOffer(@PathVariable Long id) {
        offerService.delete(id);
        return "redirect:/offers";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleInvalidReservation(IllegalArgumentException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/offers";
    }
}
