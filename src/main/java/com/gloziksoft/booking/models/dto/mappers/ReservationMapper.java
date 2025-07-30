package com.gloziksoft.booking.models.dto.mappers;

import com.gloziksoft.booking.data.entities.ReservationEntity;
import com.gloziksoft.booking.data.entities.UserEntity;
import com.gloziksoft.booking.models.dto.ReservationDTO;
import org.mapstruct.*;

/**
 * Mapper interface for converting between ReservationEntity and ReservationDTO.
 * Uses MapStruct to generate implementation automatically.
 */
@Mapper(componentModel = "spring")
public interface ReservationMapper {

    /**
     * Converts a ReservationEntity to ReservationDTO.
     * Maps user's first and last name to separate fields in DTO.
     *
     * @param entity the reservation entity to convert
     * @return the corresponding reservation DTO
     */
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "user.lastName", target = "userLastName")
    ReservationDTO toDTO(ReservationEntity entity);

    /**
     * Converts a ReservationDTO to ReservationEntity.
     * Ignores the user field during mapping.
     *
     * @param dto the reservation DTO to convert
     * @return the corresponding reservation entity
     */
    @Mapping(target = "user", ignore = true)
    ReservationEntity toEntity(ReservationDTO dto);

    /**
     * Converts a ReservationDTO to ReservationEntity,
     * setting the user entity from the context parameter.
     *
     * @param dto  the reservation DTO to convert
     * @param user the user entity to assign
     * @return the resulting reservation entity
     */
    ReservationEntity toEntity(ReservationDTO dto, @Context UserEntity user);

    /**
     * Updates an existing ReservationDTO from a ReservationEntity.
     * Maps user's first and last name accordingly.
     *
     * @param source the source entity
     * @param target the target DTO to update
     */
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "user.lastName", target = "userLastName")
    void updateReservationDTO(ReservationEntity source, @MappingTarget ReservationDTO target);

    /**
     * Updates an existing ReservationEntity from a ReservationDTO.
     *
     * @param source the source DTO
     * @param target the target entity to update
     */
    void updateReservationEntity(ReservationDTO source, @MappingTarget ReservationEntity target);
}
