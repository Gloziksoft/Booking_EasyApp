package com.booking.app.models.services;

import com.booking.app.data.entities.OfferEntity;
import com.booking.app.data.entities.UserEntity;
import com.booking.app.data.enums.ServiceType;
import com.booking.app.models.dto.ReservationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationService {

    Page<ReservationDTO> findAll(Pageable pageable);

    Page<ReservationDTO> search(String keyword, Pageable pageable);

    List<ReservationDTO> findAll();

    Page<ReservationDTO> findByUserId(Long userId, Pageable pageable);

    List<ReservationDTO> findByUserId(Long userId);

    ReservationDTO findById(Long id);

    Page<ReservationDTO> findAllByServiceType(ServiceType serviceType, Pageable pageable);

    ReservationDTO prepareReservation(Long offerId, String userEmail);

    ReservationDTO create(ReservationDTO reservationDTO, UserEntity user, OfferEntity offer);

    OfferEntity getOfferById(Long offerId);

    boolean edit(ReservationDTO reservation, org.springframework.security.core.userdetails.User user);

    void update(Long id, ReservationDTO updatedDto, org.springframework.security.core.userdetails.User user);

    void delete(Long id, org.springframework.security.core.userdetails.User user);

    List<ReservationDTO> findByUserEmail(String email);

    Page<ReservationDTO> findByUserEmail(String email, Pageable pageable);
}