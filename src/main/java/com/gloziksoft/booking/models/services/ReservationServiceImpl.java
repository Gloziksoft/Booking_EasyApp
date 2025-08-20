package com.gloziksoft.booking.models.services;

import com.gloziksoft.booking.data.entities.OfferEntity;
import com.gloziksoft.booking.data.entities.ReservationEntity;
import com.gloziksoft.booking.data.entities.UserEntity;
import com.gloziksoft.booking.data.repositories.OfferRepository;
import com.gloziksoft.booking.data.repositories.ReservationRepository;
import com.gloziksoft.booking.data.repositories.UserRepository;
import com.gloziksoft.booking.models.dto.ReservationDTO;
import com.gloziksoft.booking.models.dto.mappers.ReservationMapper;
import com.gloziksoft.booking.data.enums.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Implementation of the ReservationService interface.
 * Handles CRUD operations for reservations, including
 * mapping to/from DTOs and validating reservation rules.
 */
@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private final OfferRepository offerRepository;

    /**
     * Constructor for dependency injection.
     */
    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  OfferRepository offerRepository,
                                  ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.offerRepository = offerRepository;
        this.reservationMapper = reservationMapper;
    }

    /**
     * Retrieve all reservations with pagination.
     */
    @Override
    public Page<ReservationDTO> findAll(Pageable pageable) {
        return reservationRepository.findAll(pageable)
                .map(reservationMapper::toDTO);
    }

    /**
     * Retrieve all reservations without pagination.
     */
    @Override
    public List<ReservationDTO> findAll() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Find reservations by user ID with pagination.
     * Throws NoSuchElementException if user is not found.
     */
    @Override
    public Page<ReservationDTO> findByUserId(Long userId, Pageable pageable) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        return reservationRepository.findByUser(user, pageable)
                .map(reservationMapper::toDTO);
    }

    /**
     * Find reservations by user ID without pagination.
     */
    @Override
    public List<ReservationDTO> findByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        return reservationRepository.findByUser(user).stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Find a reservation by its ID.
     * Throws NoSuchElementException if reservation is not found.
     */
    @Override
    public ReservationDTO findById(Long id) {
        ReservationEntity entity = reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reservation not found"));
        return reservationMapper.toDTO(entity);
    }

    /**
     * Find reservations by service type with pagination.
     */
    @Override
    public Page<ReservationDTO> findAllByServiceType(ServiceType serviceType, Pageable pageable) {
        return reservationRepository.findAllByServiceType(serviceType, pageable)
                .map(reservationMapper::toDTO);
    }

    /**
     * Create a new reservation.
     * Validates that the reservation is within offer's availability
     * and that end date is after start date.
     */
    @Override
    public void create(ReservationDTO reservationDTO, UserEntity user, OfferEntity offer) {

        if (reservationDTO.getStartDateTime().isBefore(offer.getStartDateTime()) ||
                reservationDTO.getEndDateTime().isAfter(offer.getEndDateTime())) {
            throw new IllegalArgumentException("Reservation dates must be within offer availability.");
        }

        if (reservationDTO.getEndDateTime().isBefore(reservationDTO.getStartDateTime())) {
            throw new IllegalArgumentException("End date must be after start date.");
        }

        ReservationEntity reservation = reservationMapper.toEntity(reservationDTO);
        reservation.setUser(user);
        reservation.setOffer(offer);
        reservation.setTitle(offer.getTitle());
        reservation.setDescription(offer.getDescription());
        reservation.setServiceType(offer.getServiceType());

        reservationRepository.save(reservation);
    }

    /**
     * Update an existing reservation by ID.
     * Throws exceptions if reservation or offer is not found.
     */
    @Override
    public void update(Long id, ReservationDTO updatedDto) {
        ReservationEntity existing = reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reservation not found"));

        // Update fields from DTO
        reservationMapper.updateReservationEntity(updatedDto, existing);

        OfferEntity offer = offerRepository.findById(updatedDto.getOfferId())
                .orElseThrow(() -> new NoSuchElementException("Offer not found"));

        existing.setOffer(offer);
        existing.setTitle(offer.getTitle());
        existing.setDescription(offer.getDescription());
        existing.setServiceType(offer.getServiceType());

        reservationRepository.save(existing);
    }

    /**
     * Delete a reservation by its ID.
     */
    @Override
    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }

    /**
     * Find reservations by user's email without pagination.
     * Throws NoSuchElementException if user is not found.
     */
    @Override
    public List<ReservationDTO> findByUserEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        return reservationRepository.findByUser(user).stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Find reservations by user's email with pagination.
     */
    @Override
    public Page<ReservationDTO> findByUserEmail(String email, Pageable pageable) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        return reservationRepository.findByUser(user, pageable)
                .map(reservationMapper::toDTO);
    }
}
