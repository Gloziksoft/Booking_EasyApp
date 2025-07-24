package com.gloziksoft.booking.controllers;

import com.gloziksoft.booking.models.dto.ReservationDTO;
import com.gloziksoft.booking.models.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationApiController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<ReservationDTO> getReservations(@AuthenticationPrincipal User user) {
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            return reservationService.findAll();
        } else {
            return reservationService.findByUserEmail(user.getUsername());
        }
    }
}
