package com.gpa.utility;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SecurityUtilityTest {

	@Test
	void testRandomPassword() {
		assertEquals(30, SecurityUtility.randomPassword().length());
	}

}
