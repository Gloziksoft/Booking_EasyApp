package com.booking.app.models.dto.mappers;

import com.booking.app.data.entities.ReservationEntity;
import com.booking.app.models.dto.ReservationDTO;
import org.mapstruct.*;

/**
 * Maps between ReservationEntity and ReservationDTO.
 * Best practices: explicit mapping for nested fields.
 */
@Mapper(componentModel = "spring")
public interface ReservationMapper {

    // --- Entity -> DTO ---
    @Mapping(source = "offer.id", target = "offerId")
    @Mapping(source = "offer.title", target = "offerName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "user.lastName", target = "userLastName")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(target = "price", source = "price")
    ReservationDTO toDTO(ReservationEntity entity);

    // --- DTO -> Entity ---
    @Mapping(target = "offer", ignore = true) // Offer sa nastavuje v Service
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "price", source = "price")// User sa nastavuje v Service
    ReservationEntity toEntity(ReservationDTO dto);

    // --- Aktualizácia DTO z Entity ---
    @Mapping(source = "offer.id", target = "offerId")
    @Mapping(source = "offer.title", target = "offerName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "user.lastName", target = "userLastName")
    @Mapping(source = "user.email", target = "userEmail")
    void updateReservationDTO(ReservationEntity source, @MappingTarget ReservationDTO target);

    // --- Aktualizácia Entity z DTO (bez user a offer) ---
    @Mapping(target = "offer", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateReservationEntity(ReservationDTO source, @MappingTarget ReservationEntity target);
}
