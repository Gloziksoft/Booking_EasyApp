package com.gloziksoft.booking.data.repositories;

import com.gloziksoft.booking.data.entities.ReservationEntity;
import com.gloziksoft.booking.data.entities.UserEntity;
import com.gloziksoft.booking.data.enums.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing ReservationEntity persistence.
 * Extends JpaRepository to provide basic CRUD operations.
 */
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    /**
     * Finds all reservations made by a specific user.
     *
     * @param user the user entity to filter by
     * @return list of reservations belonging to the user
     */
    List<ReservationEntity> findByUser(UserEntity user);

    /**
     * Finds reservations by user with pagination support.
     *
     * @param user     the user entity to filter by
     * @param pageable pagination information
     * @return page of reservations for the given user
     */
    Page<ReservationEntity> findByUser(UserEntity user, Pageable pageable);

    /**
     * Finds reservations filtered by service type with pagination.
     *
     * @param serviceType the type of service to filter by
     * @param pageable    pagination information
     * @return page of reservations matching the service type
     */
    Page<ReservationEntity> findByServiceType(ServiceType serviceType, Pageable pageable);
}
