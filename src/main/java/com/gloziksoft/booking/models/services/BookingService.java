package com.gloziksoft.booking.models.services;

import com.gloziksoft.booking.data.entities.BookingEntity;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    List<BookingEntity> getAllBookings();
    Optional<BookingEntity> getBookingById(Long id);
    BookingEntity saveBooking(BookingEntity booking);
    void deleteBooking(Long id);
}
