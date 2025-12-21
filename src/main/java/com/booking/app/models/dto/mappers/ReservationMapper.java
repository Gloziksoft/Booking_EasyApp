package com.booking.app.models.dto.mappers;

import com.booking.app.data.entities.ReservationEntity;
import com.booking.app.data.enums.OfferTag;
import com.booking.app.models.dto.ReservationDTO;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.Set;

// ✅ Pridali sme unmappedTargetPolicy = ReportingPolicy.IGNORE
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {

    // --- Entity → DTO (ZDROJE SÚ JASNE DANÉ) ---
    @Mapping(source = "offer.id", target = "offerId")
    @Mapping(source = "offer.title", target = "offerName")
    @Mapping(source = "offer.tags", target = "tags", qualifiedByName = "toMutableSet")
    @Mapping(source = "offer.startDateTime", target = "offerStartDateTime")
    @Mapping(source = "offer.endDateTime", target = "offerEndDateTime")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "user.lastName", target = "userLastName")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "additionalServices", target = "additionalServices")
    ReservationDTO toDTO(ReservationEntity entity);

    // --- DTO → Entity (IGNORUJEME VŠETKO, ČO NIE JE V ENTITE) ---
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "offer", ignore = true)
    @Mapping(target = "user", ignore = true)
    ReservationEntity toEntity(ReservationDTO dto);

    @Named("toMutableSet")
    default Set<OfferTag> toMutableSet(Set<OfferTag> tags) {
        return tags == null ? new HashSet<>() : new HashSet<>(tags);
    }

    // --- Update entity fields from DTO ---
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "offer", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateReservationEntity(ReservationDTO source, @MappingTarget ReservationEntity target);
}