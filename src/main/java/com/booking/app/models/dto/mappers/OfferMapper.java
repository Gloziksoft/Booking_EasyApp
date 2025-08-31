package com.booking.app.models.dto.mappers;

import com.booking.app.data.entities.OfferEntity;
import com.booking.app.models.dto.OfferDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    @Mapping(source = "id", target = "offerId")
    OfferDTO toDTO(OfferEntity entity);

    @Mapping(source = "offerId", target = "id")
    OfferEntity toEntity(OfferDTO dto);

    @Mapping(source = "id", target = "offerId")
    void updateOfferDTO(OfferEntity source, @MappingTarget OfferDTO target);

    @Mapping(source = "offerId", target = "id")
    void updateOfferEntity(OfferDTO source, @MappingTarget OfferEntity target);
}
