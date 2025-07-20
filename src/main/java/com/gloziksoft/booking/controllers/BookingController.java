package com.gloziksoft.booking.controllers;

import com.gloziksoft.booking.data.entities.BookingEntity;
import com.gloziksoft.booking.data.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    // Zobrazí zoznam všetkých rezervácií
    @GetMapping
    public String listBookings(Model model) {
        model.addAttribute("bookings", bookingRepository.findAll());
        return "bookings"; // views/templates/bookings.html
    }

    // Zobrazí formulár pre novú rezerváciu
    @GetMapping("/new")
    public String showBookingForm(Model model) {
        model.addAttribute("booking", new BookingEntity());
        return "booking_form"; // views/templates/booking_form.html
    }

    // Uloží rezerváciu (z formulára)
    @PostMapping
    public String saveBooking(@ModelAttribute("booking") BookingEntity booking) {
        bookingRepository.save(booking);
        return "redirect:/bookings";
    }

    // Zmaže rezerváciu podľa ID
    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingRepository.deleteById(id);
        return "redirect:/bookings";
    }

    // Zobrazí formulár na úpravu existujúcej rezervácie
    @GetMapping("/edit/{id}")
    public String editBooking(@PathVariable Long id, Model model) {
        BookingEntity booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Neplatné ID: " + id));
        model.addAttribute("booking", booking);
        return "booking_form";
    }
}

