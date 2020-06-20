package com.gpa.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import com.gpa.domain.PasswordResetToken;
import com.gpa.domain.User;
import com.gpa.domain.security.Role;
import com.gpa.domain.security.UserRole;
import com.gpa.utility.SecurityUtility;

@SpringBootTest
@Transactional
class UserServiceTest {

	@Autowired
	private UserService userService;

	/* Test Create User */

	@Test
	void testCreateUserNullPointerException() {
		User user = null;
		Set<UserRole> userRoles = null;

		assertThrows(NullPointerException.class, () -> {
			userService.createUser(user, userRoles);
		});
	}

	@Test
	void testCreateUserExist() {
		User user = new User();
		user.setUsername("B16DCCN168");

		Set<UserRole> userRoles = null;

		assertNull(userService.createUser(user, userRoles));
	}

	@Test
	void testCreateUserHasNoRoles() {
		User user = new User();
		user.setUsername("B16DCCN444");
		user.setPassword(SecurityUtility.passwordEncoder().encode("12345"));
		user.setEmail("hungict1234@gmail.com");

		Set<UserRole> userRoles = new HashSet<>();

		User createdUser = userService.createUser(user, userRoles);
		
		assertTrue(
				createdUser.getUsername().equals("B16DCCN444") &&
				SecurityUtility.passwordEncoder().matches("12345", createdUser.getPassword()) &&
				createdUser.getEmail().equals("hungict1234@gmail.com") &&
				createdUser.getUserRoles().isEmpty()
				);
	}

	@Test
	void testCreateUserSuccess() {
		User user = new User();
		user.setUsername("B16DCCN567");
		user.setPassword(SecurityUtility.passwordEncoder().encode("54321"));
		user.setEmail("hungict4321@gmail.com");

		Role role1 = new Role();
		role1.setRoleId(1);
		role1.setName("ROLE_STUDENT");

		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, role1));
		
		User createdUser = userService.createUser(user, userRoles);
		
		assertTrue(
				createdUser.getUsername().equals("B16DCCN567") &&
				SecurityUtility.passwordEncoder().matches("54321", createdUser.getPassword()) &&
				createdUser.getEmail().equals("hungict4321@gmail.com") &&
				!createdUser.getUserRoles().isEmpty()
				);
	}

	/* TestFindByUsername */

	@Test
	void testFindByUsernameSuccess() {
		User user = userService.findByUsername("B16DCCN168");

		assertEquals(user.getUsername(), "B16DCCN168");
	}

	@Test
	void testFindByUsernameNull1() {
		User user = userService.findByUsername(null);

		assertNull(user);
	}

	@Test
	void testFindByUsernameNull2() {
		User user = userService.findByUsername("B52DCCN");

		assertNull(user);
	}

	@Test
	void testFindByUsernameNull3() {
		User user = userService.findByUsername("");

		assertNull(user);
	}

	/* TestUpdateUser */

	@Test
	void testSaveSuccess() {
		User user = userService.findByUsername("B16DCCN168");
		user.setUsername("B16Test");
		user.setPassword(SecurityUtility.passwordEncoder().encode("pass"));
		user.setEmail("email@gmail.com");

		User updatedUser = userService.save(user);

		assertTrue(
				updatedUser.getUsername().equals("B16Test") &&
				SecurityUtility.passwordEncoder().matches("pass", updatedUser.getPassword()) &&
				updatedUser.getEmail().equals("email@gmail.com") &&
				!updatedUser.getUserRoles().isEmpty()
				);
	}

	@Test
	void testSaveNull() {
		User user = userService.findByUsername(null);

		assertThrows(NullPointerException.class, () -> {
			user.setUsername("B16Test");

			userService.save(user);
		});
	}

	/* TestFindByEmail */

	@Test
	void testFindByEmail() {
		User user = userService.findByEmail("hungict100@gmail.com");

		assertEquals(user.getEmail(), "hungict100@gmail.com");
	}

	@Test
	void testFindByEmailNull1() {
		User user = userService.findByEmail("fakeemail@gmail.com");

		assertNull(user);
	}

	@Test
	void testFindByEmailNull2() {
		User user = userService.findByEmail("");

		assertNull(user);
	}

	@Test
	void testFindByEmailNull3() {
		User user = userService.findByEmail(null);

		assertNull(user);
	}

	/* TestGetPasswordResetToken */

	@Test
	void testGetPasswordResetTokenNotFound1() {
		PasswordResetToken token = userService.getPasswordResetToken("FakeToken");
		
		assertNull(token);
	}
	
	@Test
	void testGetPasswordResetTokenNotFound2() {
		PasswordResetToken token = userService.getPasswordResetToken("");
		
		assertNull(token);
	}

	@Test
	void testGetPasswordResetTokenNotFound3() {
		PasswordResetToken token = userService.getPasswordResetToken(null);
		
		assertNull(token);
	}
	
	/* TestFindById */

	@Test
	void testFindbyIdSuccess() {
		User user = userService.findbyId(2L);

		assertNotNull(user);
	}

	@Test
	void testFindbyIdNotFound1() {

		assertThrows(NoSuchElementException.class, () -> {
			userService.findbyId(100L);
		});
	}
	
	@Test
	void testFindbyIdNotFound2() {

		assertThrows(InvalidDataAccessApiUsageException.class, () -> {
			userService.findbyId(null);
		});
	}

	/* TestCreatePasswordResetToken */

	@Test
	void testCreatePasswordResetTokenForUser() {
		User user = userService.findByUsername("B16DCCN168");

		String token = UUID.randomUUID().toString();
		PasswordResetToken passToken = userService.createPasswordResetTokenForUser(user, token);

		assertEquals(token, passToken.getToken());
	}

	@Test
	void testCreatePasswordResetTokenForUserNull() {
		User user = userService.findByUsername(null);

		String token = UUID.randomUUID().toString();
		PasswordResetToken passToken = userService.createPasswordResetTokenForUser(user, token);

		assertNull(passToken.getUser());
	}
}
