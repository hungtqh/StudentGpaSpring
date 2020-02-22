package com.gpa;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gpa.domain.User;
import com.gpa.domain.security.Role;
import com.gpa.domain.security.UserRole;
import com.gpa.service.UserService;
import com.gpa.utility.SecurityUtility;

@SpringBootApplication
public class StudentGpaApplication {

	/*
	 * @Autowired private UserService userService;
	 */

	public static void main(String[] args) {
		SpringApplication.run(StudentGpaApplication.class, args);
	}

	/*
	 * @Override public void run(String... args) throws Exception { User User user1
	 * = new User(); user1.setUsername("B16DCCN168");
	 * user1.setPassword(SecurityUtility.passwordEncoder().encode("B16DCCN168"));
	 * user1.setEmail("tech.hungtq@gmail.com");
	 * 
	 * Role Role role1 = new Role(); role1.setRoleId(1);
	 * role1.setName("ROLE_STUDENT");
	 * 
	 * UserRole Set<UserRole> userRoles = new HashSet<>(); userRoles.add(new
	 * UserRole(user1, role1));
	 * 
	 * Create User userService.createUser(user1, userRoles); }
	 */
}
