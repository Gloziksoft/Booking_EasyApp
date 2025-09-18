package com.booking.app;

import com.booking.app.data.entities.OfferEntity;
import com.booking.app.data.entities.ReservationEntity;
import com.booking.app.data.entities.UserEntity;
import com.booking.app.data.repositories.ReservationRepository;
import com.booking.app.models.dto.ReservationDTO;
import com.booking.app.models.services.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
class ReservationIntegrationTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    private UserEntity user;
    private OfferEntity offer;
    private ReservationDTO dto;

    @BeforeEach
    void setUp() {
        // nastavíme používateľa
        user = new UserEntity();
        user.setId(1L);
        user.setEmail("test@test.com");

        // nastavíme ponuku
        offer = new OfferEntity();
        offer.setId(1L);
        offer.setStartDateTime(LocalDateTime.now().plusDays(1));
        offer.setEndDateTime(LocalDateTime.now().plusDays(5));
        offer.setPrice(BigDecimal.valueOf(199));

        // nastavíme DTO pre rezerváciu
        dto = new ReservationDTO();
        dto.setOfferId(offer.getId());
        dto.setStartDateTime(offer.getStartDateTime().plusHours(1));
        dto.setEndDateTime(offer.getEndDateTime().minusHours(1));
        dto.setUserEmail(user.getEmail());
    }

    @Test
    void testCreateReservationIntegration() {
        reservationService.create(dto, user, offer);

        // overenie, že sa entita uložila
        assertThat(reservationRepository.findAll()).hasSize(1);

        ReservationEntity saved = reservationRepository.findAll().get(0);
        assertThat(saved.getUser().getEmail()).isEqualTo(user.getEmail());
        assertThat(saved.getOffer().getId()).isEqualTo(offer.getId());
    }
}
