package com.gpa.service;

import java.util.Set;

import com.gpa.domain.User;
import com.gpa.domain.security.UserRole;

public interface UserService {
	User createUser(User user, Set<UserRole> userRoles);

	User findByUsername(String username);
}
