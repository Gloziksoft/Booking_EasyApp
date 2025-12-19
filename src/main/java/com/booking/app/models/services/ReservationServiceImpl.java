package com.booking.app.models.services;

import com.booking.app.data.entities.OfferEntity;
import com.booking.app.data.entities.ReservationEntity;
import com.booking.app.data.entities.UserEntity;
import com.booking.app.data.enums.OfferTag;
import com.booking.app.data.enums.ServiceType;
import com.booking.app.data.repositories.OfferRepository;
import com.booking.app.data.repositories.ReservationRepository;
import com.booking.app.data.repositories.UserRepository;
import com.booking.app.models.dto.ReservationDTO;
import com.booking.app.models.dto.mappers.OfferMapper;
import com.booking.app.models.dto.mappers.ReservationMapper;
import com.booking.app.models.services.email.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final OfferRepository offerRepository;
    private final ReservationMapper reservationMapper;
    private final EmailService emailService;

    @Value("${app.admin.email}")
    private String adminEmail;

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  UserRepository userRepository,
                                  OfferRepository offerRepository,
                                  ReservationMapper reservationMapper,
                                  EmailService emailService,
                                  OfferMapper offerMapper) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.reservationMapper = reservationMapper;
        this.emailService = emailService;
    }

    // --- STRÁNKOVANÉ ČÍTANIE (Efektívne pre veľké dáta) ---

    @Override
    @Transactional(readOnly = true)
    public Page<ReservationDTO> findAll(Pageable pageable) {
        return reservationRepository.findAll(pageable)
                .map(reservationMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReservationDTO> search(String keyword, Pageable pageable) {
        return reservationRepository.findByKeyword(keyword, pageable)
                .map(reservationMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReservationDTO> findByUserEmail(String email, Pageable pageable) {
        UserEntity user = getUserByEmail(email);
        return reservationRepository.findByUser(user, pageable)
                .map(reservationMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReservationDTO> findByUserId(Long userId, Pageable pageable) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        return reservationRepository.findByUser(user, pageable)
                .map(reservationMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReservationDTO> findAllByServiceType(ServiceType serviceType, Pageable pageable) {
        return reservationRepository.findAllByServiceType(serviceType, pageable)
                .map(reservationMapper::toDTO);
    }

    // --- KLASICKÉ CRUD ---

    @Override
    @Transactional(readOnly = true)
    public ReservationDTO findById(Long id) {
        return reservationRepository.findById(id)
                .map(reservationMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Reservation not found"));
    }

    @Override
    @Transactional
    public ReservationDTO create(ReservationDTO dto, UserEntity user, OfferEntity offer) {
        validateReservationDates(dto, offer);

        ReservationEntity reservation = new ReservationEntity();
        updateReservationFields(reservation, dto, offer);
        reservation.setUser(user);
        reservation.setOffer(offer);

        ReservationEntity saved = reservationRepository.save(reservation);
        sendConfirmationEmailsAsync(user.getEmail(), adminEmail);

        return reservationMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public void update(Long id, ReservationDTO dto, org.springframework.security.core.userdetails.User user) {
        ReservationEntity reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reservation not found"));

        if (!edit(reservationMapper.toDTO(reservation), user)) {
            throw new SecurityException("Not authorized");
        }

        OfferEntity offer = getOfferByIdInternal(dto.getOfferId());
        updateReservationFields(reservation, dto, offer);

        // Ak je admin, môže meniť aj špecifické polia
        if (isAdmin(user)) {
            reservation.setPrice(Optional.ofNullable(dto.getPrice()).orElse(offer.getPrice()));
            reservation.setServiceType(Optional.ofNullable(dto.getServiceType()).orElse(offer.getServiceType()));
        }

        reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public void delete(Long id, org.springframework.security.core.userdetails.User user) {
        ReservationDTO dto = findById(id);
        if (!edit(dto, user)) {
            throw new SecurityException("Not authorized");
        }
        reservationRepository.deleteById(id);
    }

    // --- POMOCNÉ METÓDY (Helpery) ---

    @Override
    public boolean edit(ReservationDTO reservation, org.springframework.security.core.userdetails.User user) {
        return isAdmin(user) || reservation.getUserEmail().equals(user.getUsername());
    }

    private boolean isAdmin(org.springframework.security.core.userdetails.User user) {
        return user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    private void updateReservationFields(ReservationEntity entity, ReservationDTO dto, OfferEntity offer) {
        entity.setStartDateTime(dto.getStartDateTime());
        entity.setEndDateTime(dto.getEndDateTime());
        entity.setAdults(dto.getAdults());
        entity.setChildren(dto.getChildren());
        entity.setDescription(offer.getDescription());
        entity.setPrice(offer.getPrice());
        entity.setServiceType(offer.getServiceType());
        entity.setAdditionalServices(dto.getAdditionalServices() != null ? dto.getAdditionalServices() : Set.of());
    }

    private void validateReservationDates(ReservationDTO dto, OfferEntity offer) {
        if (dto.getStartDateTime().isBefore(offer.getStartDateTime()) ||
                dto.getEndDateTime().isAfter(offer.getEndDateTime())) {
            throw new IllegalArgumentException("Dates outside of offer period.");
        }
    }

    @Async
    protected void sendConfirmationEmailsAsync(String customerEmail, String adminEmail) {
        try {
            emailService.sendReservationConfirmationEmail(customerEmail);
            emailService.sendReservationConfirmationEmail(adminEmail);
        } catch (Exception e) {
            System.err.println("Email error: " + e.getMessage());
        }
    }

    @Override
    public ReservationDTO prepareReservation(Long offerId, String userEmail) {
        OfferEntity offer = getOfferByIdInternal(offerId);
        ReservationDTO dto = new ReservationDTO();
        dto.setOfferId(offerId);
        dto.setUserEmail(userEmail);
        dto.setStartDateTime(offer.getStartDateTime());
        dto.setEndDateTime(offer.getEndDateTime());
        dto.setPrice(offer.getPrice());
        dto.setAdults(2);
        dto.setAdditionalServices(new HashSet<>());
        return dto;
    }

    // Ostatné drobné pomocné metódy z tvojho pôvodného kódu
    private UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    private OfferEntity getOfferByIdInternal(Long offerId) {
        return offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));
    }

    @Override public OfferEntity getOfferById(Long offerId) { return getOfferByIdInternal(offerId); }
    @Override public List<ReservationDTO> findAll() { return reservationRepository.findAll().stream().map(reservationMapper::toDTO).toList(); }
    @Override
    public List<ReservationDTO> findByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        // Použijeme verziu s Pageable.unpaged() aby sme nemuseli v repozitári držať List verziu
        return reservationRepository.findByUser(user, Pageable.unpaged())
                .getContent()
                .stream()
                .map(reservationMapper::toDTO)
                .toList();
    }
    @Override public List<ReservationDTO> findByUserEmail(String email) { return findByUserEmail(email, Pageable.unpaged()).getContent(); }
}