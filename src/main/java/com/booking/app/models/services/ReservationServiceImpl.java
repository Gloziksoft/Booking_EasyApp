package com.booking.app.models.services;

import com.booking.app.data.entities.OfferEntity;
import com.booking.app.data.entities.ReservationEntity;
import com.booking.app.data.entities.UserEntity;
import com.booking.app.data.repositories.OfferRepository;
import com.booking.app.data.repositories.ReservationRepository;
import com.booking.app.data.repositories.UserRepository;
import com.booking.app.models.dto.ReservationDTO;
import com.booking.app.models.dto.mappers.ReservationMapper;
import com.booking.app.data.enums.ServiceType;
import com.booking.app.models.services.email.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
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


    private ReservationRepository reservationRepository;
    private UserRepository userRepository;
    private ReservationMapper reservationMapper;
    private final OfferRepository offerRepository;
    private final EmailService emailService;
    @Value("${app.admin.email}")
    private String adminEmail;



    /**
     * Constructor for dependency injection.
     */
    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  UserRepository userRepository,
                                  OfferRepository offerRepository,
                                  ReservationMapper reservationMapper,
                                  EmailService emailService) {
        this.reservationRepository = reservationRepository;
        this.offerRepository = offerRepository;
        this.reservationMapper = reservationMapper;
        this.emailService = emailService;
        this.userRepository = userRepository;
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

    @Override
    public ReservationDTO prepareReservation(Long offerId, String userEmail) {
        OfferEntity offer = getOfferById(offerId);
        ReservationDTO reservation = new ReservationDTO();
        reservation.setOfferId(offerId);
        reservation.setTitle(offer.getTitle());
        reservation.setDescription(offer.getDescription());
        reservation.setServiceType(offer.getServiceType());
        reservation.setStartDateTime(offer.getStartDateTime());
        reservation.setEndDateTime(offer.getEndDateTime());
        reservation.setUserEmail(userEmail);
        return reservation;
    }

    @Override
    public OfferEntity getOfferById(Long offerId) {
        return offerRepository.findById(offerId)
                .orElseThrow(() -> new NoSuchElementException("Offer not found"));
    }

    @Override
    public void create(ReservationDTO reservationDTO, UserEntity user, OfferEntity offer) {
        ReservationEntity reservation = reservationMapper.toEntity(reservationDTO);
        reservation.setUser(user);
        reservation.setOffer(offer);
        reservation.setServiceType(offer.getServiceType());
        reservation.setTitle(offer.getTitle());
        reservation.setDescription(offer.getDescription());
        reservation.setServiceType(offer.getServiceType());

        if (reservation.getServiceType() == null) {
            reservation.setServiceType(offer.getServiceType());
        }

        reservationRepository.save(reservation);

        // Odoslanie e-mailu asynchrónne (bezpečné a neblokuje HTTP request)
        sendConfirmationEmailsAsync(user.getEmail(), adminEmail);
    }

    @Async
    void sendConfirmationEmailsAsync(String customerEmail, String adminEmail) {
        try {
            emailService.sendReservationConfirmationEmail(customerEmail);
            emailService.sendReservationConfirmationEmail(adminEmail);
        } catch (Exception e) {
            System.err.println("Nepodarilo sa odoslať email: " + e.getMessage());
        }
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
