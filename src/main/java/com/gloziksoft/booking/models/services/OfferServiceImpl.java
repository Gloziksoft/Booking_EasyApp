package com.gloziksoft.booking.models.services;

import com.gloziksoft.booking.data.entities.OfferEntity;
import com.gloziksoft.booking.data.enums.ServiceType;
import com.gloziksoft.booking.data.repositories.OfferRepository;
import com.gloziksoft.booking.models.dto.OfferDTO;
import com.gloziksoft.booking.models.dto.mappers.OfferMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;

    public OfferServiceImpl(OfferRepository offerRepository, OfferMapper offerMapper) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
    }

    @Override
    public List<OfferDTO> findAll() {
        return offerRepository.findAll().stream()
                .map(offerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<OfferDTO> findAll(Pageable pageable) {
        return offerRepository.findAll(pageable).map(offerMapper::toDTO);
    }

    @Override
    public OfferDTO findById(Long id) {
        return offerRepository.findById(id)
                .map(offerMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Offer with ID " + id + " not found"));
    }

    @Override
    public OfferEntity findEntityById(Long id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Offer with ID " + id + " not found"));
    }

    @Override
    public void create(OfferDTO dto) {
        OfferEntity entity = offerMapper.toEntity(dto);
        entity.setStartDateTime(dto.getStartDateTime());
        entity.setEndDateTime(dto.getEndDateTime());
        entity.setCreatedAt(java.time.LocalDateTime.now());
        offerRepository.save(entity);
    }

    @Override
    public void update(Long id, OfferDTO dto) {
        OfferEntity entity = offerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Offer with ID " + id + " not found"));
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setServiceType(dto.getServiceType());
        entity.setStartDateTime(dto.getStartDateTime());
        entity.setEndDateTime(dto.getEndDateTime());
        offerRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if (!offerRepository.existsById(id)) {
            throw new NoSuchElementException("Offer with ID " + id + " does not exist");
        }
        offerRepository.deleteById(id);
    }

    @Override
    public Page<OfferDTO> findAllByServiceType(ServiceType serviceType, Pageable pageable) {
        return offerRepository.findAllByServiceType(serviceType, pageable)
                .map(offerMapper::toDTO);
    }
}
