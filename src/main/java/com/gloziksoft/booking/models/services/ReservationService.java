package com.gloziksoft.booking.models.services;

import com.gloziksoft.booking.data.enums.ServiceType;
import com.gloziksoft.booking.models.dto.ReservationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationService {

    Page<ReservationDTO> findAll(Pageable pageable);

    List<ReservationDTO> findAll();

    Page<ReservationDTO> findByUserEmail(String email, Pageable pageable);

    List<ReservationDTO> findByUserEmail(String email);

    ReservationDTO findById(Long id);

    Page<ReservationDTO> findAllByServiceType(ServiceType serviceType, Pageable pageable);

    void create(ReservationDTO reservationDto, String userEmail);

    void update(Long id, ReservationDTO updatedDto);

    void delete(Long id);
}
