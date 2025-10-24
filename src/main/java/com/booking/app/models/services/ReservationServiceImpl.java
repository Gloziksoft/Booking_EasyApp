package com.booking.app.models.services;

import com.booking.app.data.entities.OfferEntity;
import com.booking.app.data.entities.ReservationEntity;
import com.booking.app.data.entities.UserEntity;
import com.booking.app.data.enums.OfferTag;
import com.booking.app.data.repositories.OfferRepository;
import com.booking.app.data.repositories.ReservationRepository;
import com.booking.app.data.repositories.UserRepository;
import com.booking.app.models.dto.ReservationDTO;
import com.booking.app.models.dto.mappers.OfferMapper;
import com.booking.app.models.dto.mappers.ReservationMapper;
import com.booking.app.data.enums.ServiceType;
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
    private final OfferMapper offerMapper;


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
        this.offerMapper = offerMapper;
    }

    // --- Helper metódy ---
    private UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    private OfferEntity getOfferByIdInternal(Long offerId) {
        return offerRepository.findById(offerId)
                .orElseThrow(() -> new NoSuchElementException("Offer not found"));
    }

    private ReservationDTO mapToDto(ReservationEntity entity) {
        return reservationMapper.toDTO(entity);
    }

    // --- CRUD ---
    @Override
    @Transactional(readOnly = true)
    public Page<ReservationDTO> findAll(Pageable pageable) {
        List<ReservationDTO> dtos = reservationRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return new org.springframework.data.domain.PageImpl<>(dtos, pageable, dtos.size());
    }

    @Override
    public List<ReservationDTO> findAll() {
        return reservationRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ReservationDTO> findByUserId(Long userId, Pageable pageable) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        return reservationRepository.findByUser(user, pageable).map(this::mapToDto);
    }

    @Override
    public List<ReservationDTO> findByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        return reservationRepository.findByUser(user).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ReservationDTO findById(Long id) {
        ReservationEntity reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reservation not found"));

        ReservationDTO dto = reservationMapper.toDTO(reservation);

        OfferEntity offerEntity = reservation.getOffer();
        if (offerEntity != null) {
            dto.setOfferId(offerEntity.getId());
            dto.setOfferName(offerEntity.getTitle());
            dto.setPrice(offerEntity.getPrice());
            dto.setServiceType(offerEntity.getServiceType());
            dto.setDescription(offerEntity.getDescription());
            dto.setTags(new HashSet<>(offerEntity.getTags()));
            dto.setOfferStartDateTime(offerEntity.getStartDateTime());
            dto.setOfferEndDateTime(offerEntity.getEndDateTime());
        }
        return dto;
    }

    @Override
    public Page<ReservationDTO> findAllByServiceType(ServiceType serviceType, Pageable pageable) {
        return reservationRepository.findAllByServiceType(serviceType, pageable).map(this::mapToDto);
    }

    @Async
    void sendConfirmationEmailsAsync(String customerEmail, String adminEmail) {
        try {
            emailService.sendReservationConfirmationEmail(customerEmail);
            emailService.sendReservationConfirmationEmail(adminEmail);
        } catch (Exception e) {
            System.err.println("Failed to send reservation email: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReservationDTO> findByUserEmail(String email, Pageable pageable) {
        UserEntity user = getUserByEmail(email);
        List<ReservationDTO> dtos = reservationRepository.findByUser(user).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return new org.springframework.data.domain.PageImpl<>(dtos, pageable, dtos.size());
    }

    @Override
    public ReservationDTO prepareReservation(Long offerId, String userEmail) {
        OfferEntity offer = getOfferByIdInternal(offerId);

        ReservationDTO dto = new ReservationDTO();
        dto.setOfferId(offerId);
        dto.setDescription(offer.getDescription());
        dto.setServiceType(offer.getServiceType());
        dto.setStartDateTime(offer.getStartDateTime());
        dto.setEndDateTime(offer.getEndDateTime());
        dto.setUserEmail(userEmail);
        dto.setPrice(offer.getPrice());
        dto.setAdults(2);
        dto.setChildren(0);

        // namiesto booleanov nastavíme prázdny set alebo defaultné služby
        dto.setAdditionalServices(new HashSet<>());

        return dto;
    }

    @Override
    public OfferEntity getOfferById(Long offerId) {
        return getOfferByIdInternal(offerId);
    }

    @Transactional
    @Override
    public ReservationDTO create(ReservationDTO dto, UserEntity user, OfferEntity offer) {
        OfferEntity managedOffer = offerRepository.findById(offer.getId())
                .orElseThrow(() -> new NoSuchElementException("Offer not found"));

        if (dto.getStartDateTime().isBefore(managedOffer.getStartDateTime()) ||
                dto.getEndDateTime().isAfter(managedOffer.getEndDateTime())) {
            throw new IllegalArgumentException("Reservation dates must be within the offer period.");
        }

        ReservationEntity reservation = new ReservationEntity();
        reservation.setUser(user);
        reservation.setOffer(managedOffer);
        reservation.setServiceType(managedOffer.getServiceType());
        reservation.setDescription(managedOffer.getDescription());
        reservation.setStartDateTime(dto.getStartDateTime());
        reservation.setEndDateTime(dto.getEndDateTime());
        reservation.setPrice(managedOffer.getPrice());
        reservation.setAdditionalServices(dto.getAdditionalServices() != null ? dto.getAdditionalServices() : Set.of());

        reservation.setTags(
                dto.getTags() != null ? new HashSet<>(dto.getTags()) : new HashSet<>(managedOffer.getTags())
        );
        reservation.setAdults(dto.getAdults());
        reservation.setChildren(dto.getChildren());

        reservationRepository.save(reservation);
        sendConfirmationEmailsAsync(user.getEmail(), adminEmail);

        return reservationMapper.toDTO(reservation);
    }

    @Override
    @Transactional
    public boolean edit(ReservationDTO reservation, org.springframework.security.core.userdetails.User user) {
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return isAdmin || reservation.getUserEmail().equals(user.getUsername());
    }

    @Override
    @Transactional
    public void update(Long id, ReservationDTO dto, org.springframework.security.core.userdetails.User user) {
        ReservationEntity reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reservation not found"));

        if (!edit(reservationMapper.toDTO(reservation), user)) {
            throw new SecurityException("Not authorized to edit this reservation.");
        }

        OfferEntity offer = offerRepository.findById(dto.getOfferId())
                .orElseThrow(() -> new NoSuchElementException("Offer not found"));
        reservation.setOffer(offer);

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // nastavenie základných polí
        reservation.setStartDateTime(dto.getStartDateTime());
        reservation.setEndDateTime(dto.getEndDateTime());
        reservation.setAdults(dto.getAdults());
        reservation.setChildren(dto.getChildren());

        // nastavenie podľa oprávnení
        if (isAdmin) {
            reservation.setPrice(Optional.ofNullable(dto.getPrice()).orElse(offer.getPrice()));
            reservation.setDescription(Optional.ofNullable(dto.getDescription()).orElse(offer.getDescription()));
            reservation.setServiceType(Optional.ofNullable(dto.getServiceType()).orElse(offer.getServiceType()));

            reservation.setAdditionalServices(
                    new HashSet<>(Optional.ofNullable(dto.getAdditionalServices()).orElse(Set.of()))
            );

            Set<OfferTag> updatedTags = Optional.ofNullable(dto.getTags()).orElse(Set.of());
            reservation.getTags().retainAll(updatedTags);
            reservation.getTags().addAll(updatedTags);
        } else {
            reservation.setPrice(offer.getPrice());
            reservation.setDescription(offer.getDescription());
            reservation.setServiceType(offer.getServiceType());
            reservation.setTags(new HashSet<>(offer.getTags()));
            reservation.setAdditionalServices(
                    new HashSet<>(Optional.ofNullable(dto.getAdditionalServices()).orElse(Set.of()))
            );
        }

        reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public void delete(Long id, org.springframework.security.core.userdetails.User user) {
        ReservationDTO reservation = findById(id);
        if (!edit(reservation, user)) {
            throw new SecurityException("Not authorized to delete this reservation.");
        }
        reservationRepository.deleteById(id);
    }

    @Override
    public List<ReservationDTO> findByUserEmail(String email) {
        UserEntity user = getUserByEmail(email);
        return reservationRepository.findByUser(user).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
