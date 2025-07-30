package com.gloziksoft.booking.models.dto;

import com.gloziksoft.booking.data.enums.ServiceType;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for Offer.
 * This class is used to transfer offer-related data between layers (Controller ↔ Service ↔ View).
 */
public class OfferDTO {

    // Unique identifier of the offer
    private Long id;

    // Title of the offer
    private String title;

    // Description of the offer
    private String description;

    // Start date and time of the offer availability
    private LocalDateTime startDateTime;

    // End date and time of the offer availability
    private LocalDateTime endDateTime;

    // Type of service this offer belongs to (e.g., haircut, massage)
    private ServiceType serviceType;

    // Default constructor
    public OfferDTO() {}

    // Parameterized constructor for easier instantiation
    public OfferDTO(Long id, String title, String description,
                    LocalDateTime startDateTime, LocalDateTime endDateTime,
                    ServiceType serviceType) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.serviceType = serviceType;
    }

    // --- Getters & Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }
}
