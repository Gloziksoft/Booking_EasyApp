package com.gloziksoft.booking.models.services;

import com.gloziksoft.booking.data.entities.BookingEntity;
import com.gloziksoft.booking.data.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<BookingEntity> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Optional<BookingEntity> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public BookingEntity saveBooking(BookingEntity booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
