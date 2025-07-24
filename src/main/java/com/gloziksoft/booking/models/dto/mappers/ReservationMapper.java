package com.gloziksoft.booking.models.dto.mappers;

import com.gloziksoft.booking.data.entities.ReservationEntity;
import com.gloziksoft.booking.data.entities.UserEntity;
import com.gloziksoft.booking.models.dto.ReservationDTO;

import java.time.format.DateTimeFormatter;

public class ReservationMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static ReservationDTO toDto(ReservationEntity entity) {
        ReservationDTO dto = new ReservationDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setStartDateTime(entity.getStartDateTime());
        dto.setEndDateTime(entity.getEndDateTime());
        dto.setUserId(entity.getUser() != null ? entity.getUser().getId() : null);

        // Pridanie ISO string formátov pre kalendár
        if (entity.getStartDateTime() != null) {
            dto.setStart(entity.getStartDateTime().format(FORMATTER));
        }
        if (entity.getEndDateTime() != null) {
            dto.setEnd(entity.getEndDateTime().format(FORMATTER));
        }

        return dto;
    }

    public static ReservationEntity toEntity(ReservationDTO dto, UserEntity user) {
        ReservationEntity entity = new ReservationEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setStartDateTime(dto.getStartDateTime());
        entity.setEndDateTime(dto.getEndDateTime());
        entity.setUser(user);
        return entity;
    }
}
