package com.gloziksoft.booking.models.services;

import com.gloziksoft.booking.data.entities.ReservationEntity;
import com.gloziksoft.booking.data.entities.UserEntity;
import com.gloziksoft.booking.data.enums.ServiceType;
import com.gloziksoft.booking.data.repositories.ReservationRepository;
import com.gloziksoft.booking.data.repositories.UserRepository;
import com.gloziksoft.booking.models.dto.ReservationDTO;
import com.gloziksoft.booking.models.dto.mappers.ReservationMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  UserRepository userRepository,
                                  ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.reservationMapper = reservationMapper;
    }

    /**
     * Retrieves all reservations without pagination.
     * Maps each ReservationEntity to ReservationDTO.
     */
    @Override
    public List<ReservationDTO> findAll() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all reservations with pagination support.
     * Maps each ReservationEntity to ReservationDTO.
     */
    @Override
    public Page<ReservationDTO> findAll(Pageable pageable) {
        return reservationRepository.findAll(pageable)
                .map(reservationMapper::toDTO);
    }

    /**
     * Retrieves all reservations for a user identified by their email (no pagination).
     * Returns empty list if user not found.
     */
    @Override
    public List<ReservationDTO> findByUserEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> reservationRepository.findByUser(user).stream()
                        .map(reservationMapper::toDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    /**
     * Retrieves paginated reservations for a user by email.
     * Returns empty page if user not found.
     */
    @Override
    public Page<ReservationDTO> findByUserEmail(String email, Pageable pageable) {
        return userRepository.findByEmail(email)
                .map(user -> reservationRepository.findByUser(user, pageable)
                        .map(reservationMapper::toDTO))
                .orElse(Page.empty(pageable));
    }

    /**
     * Finds a reservation by its ID.
     * Throws NoSuchElementException if not found.
     */
    @Override
    public ReservationDTO findById(Long id) {
        return reservationRepository.findById(id)
                .map(reservationMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Reservation with ID " + id + " not found."));
    }

    /**
     * Creates a new reservation for the user identified by email.
     * Throws NoSuchElementException if user not found.
     */
    @Override
    public void create(ReservationDTO dto, String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NoSuchElementException("User with email " + userEmail + " does not exist."));
        ReservationEntity entity = reservationMapper.toEntity(dto, user);
        reservationRepository.save(entity);
    }

    /**
     * Updates an existing reservation by ID with new data.
     * Throws NoSuchElementException if reservation not found.
     */
    @Override
    public void update(Long id, ReservationDTO updatedDto) {
        ReservationEntity entity = reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reservation with ID " + id + " not found."));

        reservationMapper.updateReservationEntity(updatedDto, entity);

        reservationRepository.save(entity);
    }

    /**
     * Deletes a reservation by its ID.
     * Throws NoSuchElementException if reservation does not exist.
     */
    @Override
    public void delete(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new NoSuchElementException("Reservation with ID " + id + " does not exist.");
        }
        reservationRepository.deleteById(id);
    }

    /**
     * Retrieves paginated reservations filtered by service type.
     */
    @Override
    public Page<ReservationDTO> findAllByServiceType(ServiceType serviceType, Pageable pageable) {
        return reservationRepository.findByServiceType(serviceType, pageable)
                .map(reservationMapper::toDTO);
    }
}
