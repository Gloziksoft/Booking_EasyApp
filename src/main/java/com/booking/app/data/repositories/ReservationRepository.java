package com.booking.app.data.repositories;

import com.booking.app.data.entities.ReservationEntity;
import com.booking.app.data.entities.UserEntity;
import com.booking.app.data.enums.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    @EntityGraph(attributePaths = {
            "offer",
            "offer.tags",
            "user",
            "additionalServices"
    })
    List<ReservationEntity> findByUser(UserEntity user);

    @EntityGraph(attributePaths = {
            "offer",
            "offer.tags",
            "user",
            "additionalServices"
    })
    Page<ReservationEntity> findByUser(UserEntity user, Pageable pageable);

    @EntityGraph(attributePaths = {
            "offer",
            "offer.tags",
            "user",
            "additionalServices"
    })
    Optional<ReservationEntity> findById(Long id);

    @EntityGraph(attributePaths = {
            "offer",
            "offer.tags",
            "user",
            "additionalServices"
    })
    Page<ReservationEntity> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {
            "offer",
            "offer.tags",
            "user",
            "additionalServices"
    })
    List<ReservationEntity> findAll();

    @EntityGraph(attributePaths = {
            "offer",
            "offer.tags",
            "user",
            "additionalServices"
    })
    Page<ReservationEntity> findAllByServiceType(ServiceType serviceType, Pageable pageable);

    List<ReservationEntity> findByUserId(Long userId);
    Page<ReservationEntity> findByUserId(Long userId, Pageable pageable);


    // ----------- DOPLŇENÉ METÓDY -------------
    @Query("""
        SELECT r FROM ReservationEntity r
        LEFT JOIN FETCH r.offer o
        LEFT JOIN FETCH o.tags
        LEFT JOIN FETCH r.user
        LEFT JOIN FETCH r.additionalServices
        """)
    List<ReservationEntity> findAllFetch();


    @Query("""
        SELECT r FROM ReservationEntity r
        LEFT JOIN FETCH r.offer o
        LEFT JOIN FETCH o.tags
        LEFT JOIN FETCH r.user u
        LEFT JOIN FETCH r.additionalServices
        WHERE u.email = :email
        """)
    List<ReservationEntity> findAllForUser(String email);
}

