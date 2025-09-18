package com.booking.app.models.dto;

import com.booking.app.data.enums.AdditionalService;
import com.booking.app.data.enums.ServiceType;
import com.booking.app.data.enums.OfferTag;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class ReservationDTO {

    private Long id;

    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;

    @NotNull(message = "Ponuka je povinná.")
    private Long offerId;
    private String offerName;
    private BigDecimal price;
    private ServiceType serviceType;
    private Set<OfferTag> tags;

    private Set<AdditionalService> additionalServices;
    private String description;

    @NotNull(message = "Dátum začiatku rezervácie je povinný.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDateTime;

    @NotNull(message = "Dátum ukončenia rezervácie je povinný.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDateTime;

    // Prednastavené dátumy ponuky
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime offerStartDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime offerEndDateTime;

    private Integer adults = 1;
    private Integer children = 0;

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserFirstName() { return userFirstName; }
    public void setUserFirstName(String userFirstName) { this.userFirstName = userFirstName; }

    public String getUserLastName() { return userLastName; }
    public void setUserLastName(String userLastName) { this.userLastName = userLastName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public Long getOfferId() { return offerId; }
    public void setOfferId(Long offerId) { this.offerId = offerId; }

    public String getOfferName() { return offerName; }
    public void setOfferName(String offerName) { this.offerName = offerName; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public ServiceType getServiceType() { return serviceType; }
    public void setServiceType(ServiceType serviceType) { this.serviceType = serviceType; }

    public Set<OfferTag> getTags() { return tags; }
    public void setTags(Set<OfferTag> tags) { this.tags = tags; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getStartDateTime() { return startDateTime; }
    public void setStartDateTime(LocalDateTime startDateTime) { this.startDateTime = startDateTime; }

    public LocalDateTime getEndDateTime() { return endDateTime; }
    public void setEndDateTime(LocalDateTime endDateTime) { this.endDateTime = endDateTime; }

    public LocalDateTime getOfferStartDateTime() { return offerStartDateTime; }
    public void setOfferStartDateTime(LocalDateTime offerStartDateTime) { this.offerStartDateTime = offerStartDateTime; }

    public LocalDateTime getOfferEndDateTime() { return offerEndDateTime; }
    public void setOfferEndDateTime(LocalDateTime offerEndDateTime) { this.offerEndDateTime = offerEndDateTime; }

    public Set<AdditionalService> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(Set<AdditionalService> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public Integer getChildren() { return children; }
    public void setChildren(Integer children) { this.children = children; }
}
