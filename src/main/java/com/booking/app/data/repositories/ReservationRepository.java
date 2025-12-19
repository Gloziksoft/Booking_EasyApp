package com.booking.app.data.repositories;

import com.booking.app.data.entities.ReservationEntity;
import com.booking.app.data.entities.UserEntity;
import com.booking.app.data.enums.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    // --- ZÁKLADNÉ VYHĽADÁVANIE S GRAFOM (Efektívne načítanie relácií) ---
    // EntityGraph zabezpečí, že sa informácie o ponuke a užívateľovi načítajú v jednom dopyte
    @EntityGraph(attributePaths = {"offer", "user"})
    @Query("SELECT DISTINCT r FROM ReservationEntity r")
    Page<ReservationEntity> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"offer", "user", "offer.tags", "additionalServices"})
    Optional<ReservationEntity> findById(Long id);

    @EntityGraph(attributePaths = {"offer", "user"})
    @Query("SELECT DISTINCT r FROM ReservationEntity r WHERE r.user = :user")
    Page<ReservationEntity> findByUser(@Param("user") UserEntity user, Pageable pageable);

    @EntityGraph(attributePaths = {"offer", "user", "offer.tags", "additionalServices"})
    Page<ReservationEntity> findAllByServiceType(ServiceType serviceType, Pageable pageable);

    @EntityGraph(attributePaths = {"offer", "user"})
    @Query("""
        SELECT DISTINCT r FROM ReservationEntity r 
        WHERE LOWER(r.user.email) LIKE LOWER(CONCAT('%', :keyword, '%')) 
        OR LOWER(r.offer.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    Page<ReservationEntity> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}