package com.booking.app;

import com.booking.app.data.entities.OfferEntity;
import com.booking.app.data.entities.ReservationEntity;
import com.booking.app.data.entities.UserEntity;
import com.booking.app.data.repositories.OfferRepository;
import com.booking.app.data.repositories.ReservationRepository;
import com.booking.app.data.repositories.UserRepository;
import com.booking.app.models.dto.ReservationDTO;
import com.booking.app.models.dto.mappers.OfferMapper;
import com.booking.app.models.dto.mappers.ReservationMapper;
import com.booking.app.models.services.ReservationServiceImpl;
import com.booking.app.models.services.email.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OfferRepository offerRepository;
    @Mock
    private ReservationMapper reservationMapper;
    @Mock
    private OfferMapper offerMapper;
    @Mock
    private EmailService emailService;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private UserEntity user;
    private OfferEntity offer;
    private ReservationDTO reservationDTO;
    private ReservationEntity reservationEntity;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(1L);
        user.setEmail("test@test.com");

        offer = new OfferEntity();
        offer.setId(1L);
        offer.setStartDateTime(LocalDateTime.now().plusDays(1));
        offer.setEndDateTime(LocalDateTime.now().plusDays(5));
        offer.setPrice(BigDecimal.valueOf(199));

        reservationDTO = new ReservationDTO();
        reservationDTO.setOfferId(offer.getId());
        reservationDTO.setStartDateTime(offer.getStartDateTime().plusHours(1));
        reservationDTO.setEndDateTime(offer.getEndDateTime().minusHours(1));
        reservationDTO.setUserEmail(user.getEmail());

        reservationEntity = new ReservationEntity();
    }

    @Test
    void createReservation_success() {
        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));
        when(offerRepository.findById(offer.getId()))
                .thenReturn(Optional.of(offer));
        when(reservationMapper.toEntity(reservationDTO))
                .thenReturn(reservationEntity);

        reservationService.create(reservationDTO, user, offer);

        verify(reservationRepository).save(reservationEntity);
        verify(emailService).sendReservationConfirmationEmail(user.getEmail());
    }

    @Test
    void createReservation_invalidDates_shouldThrowException() {
        reservationDTO.setStartDateTime(offer.getStartDateTime().minusDays(1));

        assertThatThrownBy(() -> reservationService.create(reservationDTO, user, offer))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
