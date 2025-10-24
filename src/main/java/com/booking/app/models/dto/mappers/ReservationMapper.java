package com.booking.app.models.dto.mappers;

import com.booking.app.data.entities.ReservationEntity;
import com.booking.app.data.enums.OfferTag;
import com.booking.app.models.dto.ReservationDTO;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    // --- Entity → DTO ---
    @Mapping(source = "offer.id", target = "offerId")
    @Mapping(source = "offer.title", target = "offerName")
    @Mapping(source = "offer.tags", target = "tags", qualifiedByName = "toMutableSet")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "user.lastName", target = "userLastName")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "additionalServices", target = "additionalServices")
    ReservationDTO toDTO(ReservationEntity entity);

    // --- DTO → Entity ---
    @InheritInverseConfiguration(name = "toDTO")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "offer", ignore = true)
    @Mapping(target = "user", ignore = true)
    ReservationEntity toEntity(ReservationDTO dto);

    // --- Helper: Set conversion (avoids immutable Set issues) ---
    @Named("toMutableSet")
    default Set<OfferTag> toMutableSet(Set<OfferTag> tags) {
        return tags == null ? new HashSet<>() : new HashSet<>(tags);
    }

    // --- Update entity fields from DTO ---
    void updateReservationEntity(ReservationDTO source, @MappingTarget ReservationEntity target);

    // --- Fill derived fields after mapping ---
    @AfterMapping
    default void fillOfferDates(@MappingTarget ReservationDTO dto, ReservationEntity entity) {
        if (entity.getOffer() != null) {
            dto.setOfferStartDateTime(entity.getOffer().getStartDateTime());
            dto.setOfferEndDateTime(entity.getOffer().getEndDateTime());
        }
    }
}
