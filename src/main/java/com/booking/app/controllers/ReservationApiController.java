package com.booking.app.controllers;

import com.booking.app.models.dto.ReservationDTO;
import com.booking.app.models.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // Marks this class as a REST controller
@RequestMapping("/api/reservations") // Base path for all endpoints in this controller
public class ReservationApiController {

    @Autowired // Injects the ReservationService bean automatically
    private ReservationService reservationService;

    /**
     * Returns a list of reservations based on the user's role.
     * - If the authenticated user has ROLE_ADMIN, return all reservations.
     * - Otherwise, return only the reservations associated with the user's email.
     *
     * @param user the currently authenticated user
     * @return a list of ReservationDTOs
     */
    @GetMapping
    public List<ReservationDTO> getReservations(@AuthenticationPrincipal User user) {
        // Check if the authenticated user has the ADMIN role
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            // Admins see all reservations
            return reservationService.findAll();
        } else {
            // Regular users see only their own reservations
            return reservationService.findByUserEmail(user.getUsername());
        }
    }
}
