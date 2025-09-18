package com.booking.app;

import com.booking.app.data.entities.OfferEntity;
import com.booking.app.data.entities.UserEntity;
import com.booking.app.data.entities.ReservationEntity;
import com.booking.app.data.repositories.ReservationRepository;
import com.booking.app.data.repositories.UserRepository;
import com.booking.app.data.repositories.OfferRepository;
import com.booking.app.models.dto.ReservationDTO;
import com.booking.app.models.dto.mappers.ReservationMapper;
import com.booking.app.models.dto.mappers.OfferMapper;
import com.booking.app.models.dto.mappers.ReservationMapperImpl;
import com.booking.app.models.services.ReservationServiceImpl;
import com.booking.app.models.services.email.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OfferRepository offerRepository;
    @Mock
    private ReservationMapper reservationMapper = new ReservationMapperImpl();
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
        MockitoAnnotations.openMocks(this);

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
        when(reservationMapper.toEntity(reservationDTO)).thenReturn(reservationEntity);
    }

    @Test
    void createReservation_success() {
        // given
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(reservationMapper.toEntity(reservationDTO)).thenReturn(reservationEntity);

        // when
        reservationService.create(reservationDTO, user, offer);

        // then
        verify(reservationRepository, times(1)).save(reservationEntity);
        verify(emailService, times(1)).sendReservationConfirmationEmail(user.getEmail());
    }

    @Test
    void createReservation_invalidDates_shouldThrowException() {
        // given
        reservationDTO.setStartDateTime(offer.getStartDateTime().minusDays(1)); // invalid

        // when / then
        assertThatThrownBy(() -> reservationService.create(reservationDTO, user, offer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("within the offer period");
    }
}
