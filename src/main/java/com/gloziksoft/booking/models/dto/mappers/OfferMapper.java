package com.gloziksoft.booking.models.dto.mappers;

import com.gloziksoft.booking.data.entities.OfferEntity;
import com.gloziksoft.booking.models.dto.OfferDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    // Entity -> DTO
    OfferDTO toDTO(OfferEntity entity);

    // DTO -> Entity
    OfferEntity toEntity(OfferDTO dto);
}
