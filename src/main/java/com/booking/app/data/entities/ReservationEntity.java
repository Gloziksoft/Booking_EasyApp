package com.booking.app.data.entities;

import com.booking.app.data.enums.AdditionalService;
import com.booking.app.data.enums.ServiceType;
import com.booking.app.data.enums.OfferTag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reservations")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Dátum začiatku rezervácie je povinný.")
    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @NotNull(message = "Dátum ukončenia rezervácie je povinný.")
    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    @Column(length = 1000)
    private String description;

    @NotNull(message = "Cena je povinná.")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @NotNull(message = "Service type is required.")
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id", nullable = false)
    private OfferEntity offer;

    @Column(name = "adults")
    private Integer adults = 1;

    @Column(name = "children")
    private Integer children = 0;

    @ElementCollection(targetClass = AdditionalService.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "reservation_additional_services", joinColumns = @JoinColumn(name = "reservation_id"))
    @Column(name = "service")
    private Set<AdditionalService> additionalServices = new HashSet<>();

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getStartDateTime() { return startDateTime; }
    public void setStartDateTime(LocalDateTime startDateTime) { this.startDateTime = startDateTime; }

    public LocalDateTime getEndDateTime() { return endDateTime; }
    public void setEndDateTime(LocalDateTime endDateTime) { this.endDateTime = endDateTime; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    public ServiceType getServiceType() { return serviceType; }
    public void setServiceType(ServiceType serviceType) { this.serviceType = serviceType; }

    public OfferEntity getOffer() { return offer; }
    public void setOffer(OfferEntity offer) { this.offer = offer; }

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public Integer getChildren() { return children; }
    public void setChildren(Integer children) { this.children = children; }

    public Set<AdditionalService> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(Set<AdditionalService> additionalServices) {
        this.additionalServices = additionalServices;
    }
}
