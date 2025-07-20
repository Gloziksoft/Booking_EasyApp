package com.gloziksoft.booking.models.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class BookingDTO {

    private Long id;

    @NotBlank(message = "Meno zákazníka je povinné.")
    private String customerName;

    @NotNull(message = "Dátum rezervácie je povinný.")
    @FutureOrPresent(message = "Rezervácia nemôže byť v minulosti.")
    private LocalDate bookingDate;

    private String notes;

    // Constructors
    public BookingDTO() {
    }

    public BookingDTO(Long id, String customerName, LocalDate bookingDate, String notes) {
        this.id = id;
        this.customerName = customerName;
        this.bookingDate = bookingDate;
        this.notes = notes;
    }

    // Gettery a settery
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
