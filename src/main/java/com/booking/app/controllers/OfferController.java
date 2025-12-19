package com.booking.app.controllers;

import com.booking.app.data.enums.ServiceType;
import com.booking.app.models.dto.OfferDTO;
import com.booking.app.models.services.OfferService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    // --- LIST ---
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

    // --- DETAIL ---
    @GetMapping("/{id}")
    public String offerDetail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        OfferDTO offer = offerService.findById(id);
        if (offer == null) {
            redirectAttributes.addFlashAttribute("error", "Offer not found");
            return "redirect:/offers";
        }

        model.addAttribute("offer", offer);
        return "pages/offers/detail";
    }

    // --- CREATE ---
    @GetMapping("/create")
    public String createOfferForm(Model model) {
        model.addAttribute("offer", new OfferDTO());
        model.addAttribute("serviceTypes", ServiceType.values());
        return "pages/offers/create";
    }

    @PostMapping("/create")
    public String createOfferSubmit(@Valid @ModelAttribute("offer") OfferDTO offerDTO,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("serviceTypes", ServiceType.values());
            return "pages/offers/create";
        }

        offerService.create(offerDTO);
        return "redirect:/offers";
    }

    // --- EDIT ---
    @GetMapping("/{id}/edit")
    public String editOfferForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        OfferDTO offer = offerService.findById(id);
        if (offer == null) {
            redirectAttributes.addFlashAttribute("error", "Offer not found");
            return "redirect:/offers";
        }

        model.addAttribute("offer", offer);
        model.addAttribute("serviceTypes", ServiceType.values());
        return "pages/offers/edit";
    }

    @PostMapping("/{id}/edit")
    public String editOfferSubmit(@PathVariable Long id,
                                  @Valid @ModelAttribute("offer") OfferDTO offerDTO,
                                  BindingResult bindingResult,
                                  Model model) { // <-- pridané
        if (bindingResult.hasErrors()) {
            model.addAttribute("serviceTypes", ServiceType.values());
            return "pages/offers/edit";
        }

        offerService.update(id, offerDTO);
        return "redirect:/offers/" + id;
    }

    // --- DELETE ---
    @PostMapping("/{id}/delete")
    public String deleteOffer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        offerService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Offer deleted successfully");
        return "redirect:/offers";
    }
}
