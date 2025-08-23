package com.booking.app.models.dto.mappers;

import com.booking.app.data.entities.ReservationEntity;
import com.booking.app.models.dto.ReservationDTO;
import org.mapstruct.*;

/**
 * Maps between ReservationEntity and ReservationDTO.
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
    ReservationDTO toDTO(ReservationEntity entity);


    @Mapping(target = "user", ignore = true)
    @Mapping(target = "offer", ignore = true)
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "startDateTime", target = "startDateTime")
    @Mapping(source = "endDateTime", target = "endDateTime")
    @Mapping(source = "serviceType", target = "serviceType")
    ReservationEntity toEntity(ReservationDTO dto);

    // --- Aktualizácia DTO z Entity ---
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "user.lastName", target = "userLastName")
    void updateReservationDTO(ReservationEntity source, @MappingTarget ReservationDTO target);

    // --- Aktualizácia Entity z DTO (bez user) ---
    @Mapping(target = "user", ignore = true)
    void updateReservationEntity(ReservationDTO source, @MappingTarget ReservationEntity target);
}
