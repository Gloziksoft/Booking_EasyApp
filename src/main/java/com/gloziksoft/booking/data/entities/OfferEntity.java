package com.gloziksoft.booking.models.entities;

import com.gloziksoft.booking.data.enums.ServiceType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * JPA Entity representing an Offer stored in the database.
 * Maps to the "offers" table.
 */
@Entity
@Table(name = "offers")
public class OfferEntity {

    // Primary key with auto-increment strategy
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Title of the offer
    private String title;

    // Detailed description of the offer (up to 1000 characters)
    @Column(length = 1000)
    private String description;

    // Offer's start date and time
    private LocalDateTime startDateTime;

    // Offer's end date and time
    private LocalDateTime endDateTime;

    // Enum representing the service type (stored as STRING in DB)
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

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
