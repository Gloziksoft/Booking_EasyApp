package com.booking.app;

import com.booking.app.data.entities.OfferEntity;
import com.booking.app.data.entities.UserEntity;
import com.booking.app.data.enums.Role;
import com.booking.app.data.enums.ServiceType;
import com.booking.app.data.repositories.OfferRepository;
import com.booking.app.data.repositories.UserRepository;
import com.booking.app.models.dto.ReservationDTO;
import com.booking.app.models.services.ReservationService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class ReservationServiceTest {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	void testReservationOutsideOfferDates() {
		// vytvorenie ponuky pomocou setrov
		OfferEntity offer = new OfferEntity();
		offer.setTitle("Ponuka");
		offer.setDescription("Desc");
		offer.setPrice(BigDecimal.valueOf(100));
		offer.setStartDateTime(LocalDateTime.of(2025, 8, 1, 10, 0));
		offer.setEndDateTime(LocalDateTime.of(2025, 8, 10, 10, 0));
		offer.setServiceType(ServiceType.PARKOVANIE);

		OfferEntity savedOffer = offerRepository.save(offer);

		// vytvorenie usera pomocou setrov
		UserEntity user = new UserEntity();
		user.setEmail("test" + System.currentTimeMillis() + "@test.com");
		user.setPassword("heslo");
		user.setFirstName("Test");
		user.setLastName("User");
		user.setRole(Role.USER);
		UserEntity savedUser = userRepository.save(user);


		// DTO mimo povoleného intervalu
		ReservationDTO dto = new ReservationDTO();
		dto.setOfferId(savedOffer.getId());
		dto.setStartDateTime(LocalDateTime.of(2025, 7, 31, 10, 0)); // ❌ pred začiatkom
		dto.setEndDateTime(LocalDateTime.of(2025, 8, 5, 10, 0));


		assertThrows(IllegalArgumentException.class, () -> {
			reservationService.create(dto, savedUser, savedOffer);
		});
	}
}
