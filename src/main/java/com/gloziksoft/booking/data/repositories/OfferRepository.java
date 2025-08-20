package com.gloziksoft.booking.data.repositories;

import com.gloziksoft.booking.data.entities.OfferEntity;
import com.gloziksoft.booking.data.enums.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<OfferEntity, Long> {
    Page<OfferEntity> findAllByServiceType(ServiceType serviceType, Pageable pageable);
}
