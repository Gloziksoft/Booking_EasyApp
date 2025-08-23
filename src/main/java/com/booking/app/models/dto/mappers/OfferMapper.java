package com.booking.app.models.dto.mappers;

import com.booking.app.data.entities.OfferEntity;
import com.booking.app.models.dto.OfferDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    // Entity -> DTO
    OfferDTO toDTO(OfferEntity entity);

    // DTO -> Entity
    OfferEntity toEntity(OfferDTO dto);
}
