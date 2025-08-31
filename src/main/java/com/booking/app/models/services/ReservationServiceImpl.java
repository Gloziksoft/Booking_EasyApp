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
                                  EmailService emailService) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.reservationMapper = reservationMapper;
        this.emailService = emailService;
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
    public Page<ReservationDTO> findAll(Pageable pageable) {
        return reservationRepository.findAll(pageable).map(this::mapToDto);
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

    @Override
    public ReservationDTO findById(Long id) {
        ReservationEntity entity = reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reservation not found"));
        return mapToDto(entity);
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
    public Page<ReservationDTO> findByUserEmail(String email, Pageable pageable) {
        UserEntity user = getUserByEmail(email);
        return reservationRepository.findByUser(user, pageable).map(this::mapToDto);
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
        return dto;
    }

    @Override
    public OfferEntity getOfferById(Long offerId) {
        return getOfferByIdInternal(offerId);
    }

    @Override
    public void create(ReservationDTO dto, UserEntity user, OfferEntity offer) {
        if (dto.getStartDateTime().isBefore(offer.getStartDateTime()) ||
                dto.getEndDateTime().isAfter(offer.getEndDateTime())) {
            throw new IllegalArgumentException("Reservation dates must be within the offer period.");
        }

        ReservationEntity reservation = reservationMapper.toEntity(dto);
        reservation.setUser(user);
        reservation.setOffer(offer);
        reservation.setServiceType(offer.getServiceType());
        reservation.setDescription(offer.getDescription());
        reservation.setPrice(offer.getPrice());

        reservationRepository.save(reservation);
        sendConfirmationEmailsAsync(user.getEmail(), adminEmail);
    }

    @Override
    public boolean edit(ReservationDTO reservation, org.springframework.security.core.userdetails.User user) {
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return isAdmin || reservation.getUserEmail().equals(user.getUsername());
    }

    public void update(Long id, ReservationDTO updatedDto, org.springframework.security.core.userdetails.User user) {
        ReservationDTO existing = findById(id);
        if (!edit(existing, user)) {
            throw new SecurityException("Not authorized to edit this reservation.");
        }

        ReservationEntity entity = reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reservation not found"));

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // Update entity from DTO (without offer/user)
        reservationMapper.updateReservationEntity(updatedDto, entity);

        // Nastavenie offer a user
        OfferEntity offer = offerRepository.findById(updatedDto.getOfferId())
                .orElseThrow(() -> new NoSuchElementException("Offer not found"));
        entity.setOffer(offer);

        UserEntity userEntity = entity.getUser(); // user sa nemení
        entity.setUser(userEntity);

        // Admin môže meniť cenu, popis, typ služby
        if (isAdmin) {
            entity.setPrice(updatedDto.getPrice());
            entity.setDescription(updatedDto.getDescription());
            entity.setServiceType(updatedDto.getServiceType());
        } else {
            // user nemôže meniť cenu/popis, použijeme pôvodné hodnoty z Offer
            entity.setPrice(offer.getPrice());
            entity.setDescription(offer.getDescription());
            entity.setServiceType(offer.getServiceType());
        }

        reservationRepository.save(entity);
    }


    @Override
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
