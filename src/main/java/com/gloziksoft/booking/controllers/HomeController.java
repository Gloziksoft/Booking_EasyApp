package com.gloziksoft.booking.controllers;

import com.gloziksoft.booking.data.enums.ServiceType;
import com.gloziksoft.booking.models.dto.ReservationDTO;
import com.gloziksoft.booking.models.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    private ReservationService reservationService;

    /**
     * Renders the home page.
     *
     * @return view name of the home page
     */
    @GetMapping("/")
    public String renderIndex() {
        return "pages/home/index";
    }

    /**
     * Renders the "About Us" page.
     *
     * @return view name of the about-us page
     */
    @GetMapping("/about-us")
    public String renderAboutUs() {
        return "pages/home/about-us";
    }

    /**
     * Displays a paginated list of offers.
     * Optionally filters offers by service type.
     *
     * @param model Spring model to pass data to the view
     * @param page current page number (default is 0)
     * @param size number of items per page (default is 6)
     * @param serviceType optional filter by service type
     * @return view name of the offers listing page
     */
    @GetMapping("/offers")
    public String offers(Model model,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "6") int size,
                         @RequestParam(name = "serviceType", required = false) ServiceType serviceType) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReservationDTO> reservationPage;

        if (serviceType != null) {
            // Filter offers by selected service type
            reservationPage = reservationService.findAllByServiceType(serviceType, pageable);
        } else {
            // Fetch all offers without filtering
            reservationPage = reservationService.findAll(pageable);
        }

        model.addAttribute("reservations", reservationPage.getContent());
        model.addAttribute("currentPage", reservationPage.getNumber());
        model.addAttribute("totalPages", reservationPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("serviceType", serviceType);

        return "pages/home/offers";
    }

    /**
     * Shows detailed view of a single offer identified by its ID.
     *
     * @param id the offer id
     * @param model Spring model to pass data to the view
     * @return offer detail page or redirect to offers if not found
     */
    @GetMapping("/offers/{id}")
    public String offerDetail(@PathVariable Long id, Model model) {
        ReservationDTO reservation = reservationService.findById(id);
        if (reservation == null) {
            // Redirect to offers page if offer not found
            return "redirect:/offers";
        }
        model.addAttribute("reservation", reservation);
        return "pages/home/offer-detail";
    }

    /**
     * Displays reservation form for a specific offer.
     * Only accessible to authenticated users.
     * Redirects unauthenticated users to login page.
     *
     * @param id the offer id
     * @param authentication authentication object representing the current user
     * @param model Spring model to pass data to the view
     * @return reservation page or redirect to login if unauthenticated
     */
    @GetMapping("/offers/{id}/reserve")
    public String reserveOffer(@PathVariable Long id, Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            // Redirect unauthenticated users to login with redirect back to offer detail
            return "redirect:/login?redirect=/offers/" + id;
        }

        // TODO: Implement actual reservation creation logic here

        // For now, show reservation detail page with reserve option
        ReservationDTO reservation = reservationService.findById(id);
        if (reservation == null) {
            return "redirect:/offers";
        }

        model.addAttribute("reservation", reservation);
        return "pages/home/offer-reserve";
    }
}
