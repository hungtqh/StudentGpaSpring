package com.gpa.service;

import java.util.Set;

import com.gpa.domain.PasswordResetToken;
import com.gpa.domain.User;
import com.gpa.domain.security.UserRole;

public interface UserService {
	User createUser(User user, Set<UserRole> userRoles);

	User findByUsername(String username);

	User save(User user);

	User findByEmail(String userEmail);

	PasswordResetToken getPasswordResetToken(String token);

	User findbyId(Long id);
	
	PasswordResetToken createPasswordResetTokenForUser(User user, String token);
}
