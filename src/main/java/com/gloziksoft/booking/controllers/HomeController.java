package com.gloziksoft.booking.controllers;

import com.gloziksoft.booking.data.enums.ServiceType;
import com.gloziksoft.booking.models.dto.ReservationDTO;
import com.gloziksoft.booking.models.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/")
    public String renderIndex() {
        return "pages/home/index";
    }

    @GetMapping("/about-us")
    public String renderAboutUs() {
        return "pages/home/about-us";
    }

    @GetMapping("/offers")
    public String offers(Model model,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "6") int size,
                         @RequestParam(name = "serviceType", required = false) ServiceType serviceType) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReservationDTO> reservationPage;

        if (serviceType != null) {
            reservationPage = reservationService.findAllByServiceType(serviceType, pageable);
        } else {
            reservationPage = reservationService.findAll(pageable);
        }

        model.addAttribute("reservations", reservationPage.getContent());
        model.addAttribute("currentPage", reservationPage.getNumber());
        model.addAttribute("totalPages", reservationPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("serviceType", serviceType);

        return "pages/home/offers";
    }
}
