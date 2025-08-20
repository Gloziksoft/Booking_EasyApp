package com.gloziksoft.booking.data.repositories;

import com.gloziksoft.booking.data.entities.ReservationEntity;
import com.gloziksoft.booking.data.entities.UserEntity;
import com.gloziksoft.booking.data.enums.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    // Hľadanie podľa celého objektu UserEntity
    List<ReservationEntity> findByUser(UserEntity user);
    Page<ReservationEntity> findByUser(UserEntity user, Pageable pageable);

    // Hľadanie podľa serviceType
    Page<ReservationEntity> findAllByServiceType(ServiceType serviceType, Pageable pageable);

    // Prípadne ďalšie metódy, napr. podľa userId
    List<ReservationEntity> findByUserId(Long userId);
    Page<ReservationEntity> findByUserId(Long userId, Pageable pageable);
}
