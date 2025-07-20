package com.gloziksoft.booking.data.repositories;

import com.gloziksoft.booking.data.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    // Môžeš pridať aj vlastné query metódy, napríklad:
    // List<Booking> findByCustomerName(String customerName);
}
