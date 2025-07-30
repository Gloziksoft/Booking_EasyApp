package com.gloziksoft.booking.models.services;

import com.gloziksoft.booking.data.enums.ServiceType;
import com.gloziksoft.booking.models.dto.ReservationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing reservations.
 * Defines methods for retrieving, creating, updating, and deleting reservations.
 */
public interface ReservationService {

    /**
     * Retrieves all reservations with pagination support.
     *
     * @param pageable pagination information (page number, size, sorting)
     * @return paged list of ReservationDTOs
     */
    Page<ReservationDTO> findAll(Pageable pageable);

    /**
     * Retrieves all reservations without pagination.
     *
     * @return list of all ReservationDTOs
     */
    List<ReservationDTO> findAll();

    /**
     * Retrieves all reservations for a specific user identified by their email with pagination.
     *
     * @param email user's email
     * @param pageable pagination information
     * @return paged list of ReservationDTOs for the user
     */
    Page<ReservationDTO> findByUserEmail(String email, Pageable pageable);

    /**
     * Retrieves all reservations for a specific user identified by their email without pagination.
     *
     * @param email user's email
     * @return list of ReservationDTOs for the user
     */
    List<ReservationDTO> findByUserEmail(String email);

    /**
     * Finds a single reservation by its unique identifier.
     *
     * @param id reservation id
     * @return the ReservationDTO if found, otherwise null
     */
    ReservationDTO findById(Long id);

    /**
     * Retrieves all reservations filtered by a specific service type with pagination.
     *
     * @param serviceType type of service (e.g. accommodation, transport)
     * @param pageable pagination information
     * @return paged list of ReservationDTOs matching the service type
     */
    Page<ReservationDTO> findAllByServiceType(ServiceType serviceType, Pageable pageable);

    /**
     * Creates a new reservation associated with the user identified by email.
     *
     * @param reservationDto data transfer object containing reservation details
     * @param userEmail email of the user making the reservation
     */
    void create(ReservationDTO reservationDto, String userEmail);

    /**
     * Updates an existing reservation identified by id with new data.
     *
     * @param id reservation id to update
     * @param updatedDto DTO containing updated reservation data
     */
    void update(Long id, ReservationDTO updatedDto);

    /**
     * Deletes a reservation identified by id.
     *
     * @param id reservation id to delete
     */
    void delete(Long id);
}
