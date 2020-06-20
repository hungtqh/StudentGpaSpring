package com.gpa.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserSecurityServiceTest {

	@Autowired
	private UserSecurityService userSercurityService;

	@Test
	void testLoadUserByUsernameSuccess() {
		UserDetails userDetails = userSercurityService.loadUserByUsername("B16DCCN168");

		assertEquals("B16DCCN168", userDetails.getUsername());
	}

	@Test
	void testLoadUserByUsernameFail() {
	
		assertThrows(UsernameNotFoundException.class, () -> {
			userSercurityService.loadUserByUsername("FakeID");
		});
	}
}
