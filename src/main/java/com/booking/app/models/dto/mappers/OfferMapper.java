package com.booking.app.models.dto.mappers;

import com.booking.app.data.entities.OfferEntity;
import com.booking.app.data.enums.OfferTag;
import com.booking.app.models.dto.OfferDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    @Mapping(source = "id", target = "offerId")
    @Mapping(source = "tags", target = "tags") // pridáme mapovanie tagov
    OfferDTO toDTO(OfferEntity entity);

    @Mapping(source = "offerId", target = "id")
    @Mapping(source = "tags", target = "tags") // pridáme mapovanie tagov
    OfferEntity toEntity(OfferDTO dto);

    @Mapping(source = "id", target = "offerId")
    @Mapping(source = "tags", target = "tags")
    void updateOfferDTO(OfferEntity source, @MappingTarget OfferDTO target);

    @Mapping(source = "tags", target = "tags")
    @Mapping(source = "offerId", target = "id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOfferEntity(OfferDTO source, @MappingTarget OfferEntity target);
}
