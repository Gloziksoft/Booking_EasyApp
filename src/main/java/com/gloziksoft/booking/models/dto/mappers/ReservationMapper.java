package com.gloziksoft.booking.models.dto.mappers;

import com.gloziksoft.booking.data.entities.ReservationEntity;
import com.gloziksoft.booking.data.entities.UserEntity;
import com.gloziksoft.booking.models.dto.ReservationDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationDTO toDTO(ReservationEntity entity);

    @Mapping(target = "user", ignore = true)
    ReservationEntity toEntity(ReservationDTO dto);

    ReservationEntity toEntity(ReservationDTO dto, @Context UserEntity user);

    void updateReservationDTO(ReservationEntity source, @MappingTarget ReservationDTO target);

    void updateReservationEntity(ReservationDTO source, @MappingTarget ReservationEntity target);
}

