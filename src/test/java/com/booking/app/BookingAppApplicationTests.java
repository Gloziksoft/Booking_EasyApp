package com.booking.app;

import com.booking.app.models.services.email.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BookingAppApplicationTests {

	@MockBean
	private EmailService emailService;

	@Test
	void contextLoads() {
	}
}
