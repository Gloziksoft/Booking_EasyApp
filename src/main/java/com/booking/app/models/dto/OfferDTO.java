package com.booking.app.models.dto;

import com.booking.app.data.enums.ServiceType;
import com.booking.app.data.enums.OfferTag;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class OfferDTO {

    private Long offerId;
    private String title;
    private String description;
    private BigDecimal price;
    private ServiceType serviceType;
    private Set<OfferTag> tags = new HashSet<>();
    private LocalDateTime createdAt;

    @NotNull(message = "Dátum začiatku služby je povinný.")
    private LocalDateTime startDateTime;

    @NotNull(message = "Dátum ukončenia služby je povinný.")
    private LocalDateTime endDateTime;

    // --- Getters & Setters ---
    public Long getOfferId() { return offerId; }
    public void setOfferId(Long offerId) { this.offerId = offerId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public ServiceType getServiceType() { return serviceType; }
    public void setServiceType(ServiceType serviceType) { this.serviceType = serviceType; }

    public Set<OfferTag> getTags() { return tags; }
    public void setTags(Set<OfferTag> tags) { this.tags = tags; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getStartDateTime() { return startDateTime; }
    public void setStartDateTime(LocalDateTime startDateTime) { this.startDateTime = startDateTime; }

    public LocalDateTime getEndDateTime() { return endDateTime; }
    public void setEndDateTime(LocalDateTime endDateTime) { this.endDateTime = endDateTime; }
}
