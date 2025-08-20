package com.gloziksoft.booking.models.services;

import com.gloziksoft.booking.data.entities.OfferEntity;
import com.gloziksoft.booking.data.enums.ServiceType;
import com.gloziksoft.booking.models.dto.OfferDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OfferService {

    List<OfferDTO> findAll();

    Page<OfferDTO> findAll(Pageable pageable);

    OfferDTO findById(Long id);

    void create(OfferDTO dto);

    void update(Long id, OfferDTO dto);

    void delete(Long id);

    Page<OfferDTO> findAllByServiceType(ServiceType serviceType, Pageable pageable);

    // Nová metóda, ktorá vráti entitu
    OfferEntity findEntityById(Long id);
}
