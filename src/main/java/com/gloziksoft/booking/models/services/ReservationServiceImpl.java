package com.gloziksoft.booking.models.services;

import com.gloziksoft.booking.data.entities.ReservationEntity;
import com.gloziksoft.booking.data.entities.UserEntity;
import com.gloziksoft.booking.data.enums.ServiceType;
import com.gloziksoft.booking.data.repositories.ReservationRepository;
import com.gloziksoft.booking.data.repositories.UserRepository;
import com.gloziksoft.booking.models.dto.ReservationDTO;
import com.gloziksoft.booking.models.dto.mappers.ReservationMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<ReservationDTO> findAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ReservationDTO> findAll(Pageable pageable) {
        return reservationRepository.findAll(pageable)
                .map(ReservationMapper::toDto);
    }

    @Override
    public List<ReservationDTO> findByUserEmail(String email) {
        UserEntity user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return List.of();
        }
        return reservationRepository.findByUser(user).stream()
                .map(ReservationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ReservationDTO> findByUserEmail(String email, Pageable pageable) {
        UserEntity user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return Page.empty(pageable);
        }
        return reservationRepository.findByUser(user, pageable)
                .map(ReservationMapper::toDto);
    }

    @Override
    public ReservationDTO findById(Long id) {
        return reservationRepository.findById(id)
                .map(ReservationMapper::toDto)
                .orElse(null);
    }

    @Override
    public void create(ReservationDTO dto, String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail).orElseThrow();
        ReservationEntity entity = ReservationMapper.toEntity(dto, user);
        reservationRepository.save(entity);
    }

    @Override
    public void update(Long id, ReservationDTO updatedDto) {
        ReservationEntity entity = reservationRepository.findById(id).orElseThrow();
        entity.setTitle(updatedDto.getTitle());
        entity.setDescription(updatedDto.getDescription());
        entity.setStartDateTime(updatedDto.getStartDateTime());
        entity.setEndDateTime(updatedDto.getEndDateTime());
        reservationRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public Page<ReservationDTO> findAllByServiceType(ServiceType serviceType, Pageable pageable) {
        return reservationRepository.findByServiceType(serviceType, pageable)
                .map(ReservationMapper::toDto);
    }
}
