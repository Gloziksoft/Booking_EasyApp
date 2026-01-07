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

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    // --------------------------------------------------------------------
    // PAGINOVANÉ VÝPISY (ADMIN / LISTY)
    // --------------------------------------------------------------------

    /**
     * Základný admin výpis – ponuka + user načítané eager
     */
    @EntityGraph(attributePaths = {"offer", "user"})
    @Query("SELECT DISTINCT r FROM ReservationEntity r")
    Page<ReservationEntity> findAll(Pageable pageable);

    /**
     * Výpis rezervácií pre konkrétneho usera (stránkované)
     */
    @EntityGraph(attributePaths = {"offer", "user"})
    @Query("SELECT DISTINCT r FROM ReservationEntity r WHERE r.user = :user")
    Page<ReservationEntity> findByUser(@Param("user") UserEntity user, Pageable pageable);

    /**
     * Výpis rezervácií podľa typu služby (admin)
     */
    @EntityGraph(attributePaths = {"offer", "user"})
    Page<ReservationEntity> findAllByServiceType(ServiceType serviceType, Pageable pageable);

    /**
     * Fulltext / keyword vyhľadávanie (admin)
     */
    @EntityGraph(attributePaths = {"offer", "user"})
    @Query("""
        SELECT DISTINCT r FROM ReservationEntity r
        WHERE LOWER(r.user.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(r.offer.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    Page<ReservationEntity> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // --------------------------------------------------------------------
    // DETAIL / PROFIL – NEPAGINOVANÉ (KRITICKÉ PRE /account/profile)
    // --------------------------------------------------------------------

    /**
     * Detail rezervácie – všetky väzby natiahnuté
     */
    @EntityGraph(attributePaths = {
            "offer",
            "offer.tags",
            "user",
            "additionalServices"
    })
    Optional<ReservationEntity> findById(Long id);

    /**
     * PROFIL USERA – všetky jeho rezervácie
     * (nepaginované, bezpečné pre Thymeleaf)
     */
    @EntityGraph(attributePaths = {
            "offer",
            "user"
    })
    @Query("""
        SELECT DISTINCT r FROM ReservationEntity r
        WHERE r.user.email = :email
    """)
    List<ReservationEntity> findAllByUserEmail(@Param("email") String email);

    /**
     * ADMIN PROFIL – všetky rezervácie
     * (bez lazy problémov)
     */
    @EntityGraph(attributePaths = {
            "offer",
            "user"
    })
    @Query("SELECT DISTINCT r FROM ReservationEntity r")
    List<ReservationEntity> findAllForProfile();
}
