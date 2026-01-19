package com.booking.app.data.repositories;

import com.booking.app.data.entities.OfferEntity;
import com.booking.app.data.enums.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfferRepository extends JpaRepository<OfferEntity, Long> {
    Page<OfferEntity> findAllByServiceType(ServiceType serviceType, Pageable pageable);
}
