package com.booking.app.models.services;

import com.booking.app.data.entities.OfferEntity;
import com.booking.app.data.enums.ServiceType;
import com.booking.app.models.dto.OfferDTO;
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

    OfferEntity findEntityById(Long id);
}
