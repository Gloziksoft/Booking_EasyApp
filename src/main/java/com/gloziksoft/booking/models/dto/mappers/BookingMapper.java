package com.gloziksoft.booking.models.dto.mappers;

import com.gloziksoft.booking.data.entities.BookingEntity;
import com.gloziksoft.booking.models.dto.BookingDTO;

public class BookingMapper {

    // Booking → BookingDTO
    public static BookingDTO toDto(BookingEntity booking) {
        if (booking == null) {
            return null;
        }

        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setCustomerName(booking.getCustomerName());
        dto.setBookingDate(booking.getBookingDate());
        dto.setNotes(booking.getNotes());

        return dto;
    }

    // BookingDTO → Booking
    public static BookingEntity toEntity(BookingDTO dto) {
        if (dto == null) {
            return null;
        }

        BookingEntity booking = new BookingEntity();
        booking.setId(dto.getId());
        booking.setCustomerName(dto.getCustomerName());
        booking.setBookingDate(dto.getBookingDate());
        booking.setNotes(dto.getNotes());

        return booking;
    }
}
