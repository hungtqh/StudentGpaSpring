package com.gpa;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.gpa.domain.User;
import com.gpa.domain.security.Role;
import com.gpa.domain.security.UserRole;
import com.gpa.service.UserService;
import com.gpa.utility.SecurityUtility;

@SpringBootApplication
public class StudentGpaApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(StudentGpaApplication.class, args);
	}

	// add support for war(web application archive packaging
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(StudentGpaApplication.class);
	}

	@Override
	public void run(String... args) throws Exception {
		User user1 = new User();
		user1.setUsername("B17DCCN123");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("12345"));
		user1.setEmail("hungict100@gmail.com");

		Role role1 = new Role();
		role1.setRoleId(1);
		role1.setName("ROLE_STUDENT");

		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user1, role1));

		userService.createUser(user1, userRoles);
		
		User user2 = new User();
		user2.setUsername("B16DCCN168");
		user2.setPassword(SecurityUtility.passwordEncoder().encode("12345"));
		user2.setEmail("tech.hungtq@gmail.com");

		Role role2 = new Role();
		role2.setRoleId(1);
		role2.setName("ROLE_STUDENT");

		Set<UserRole> userRoles2 = new HashSet<>();
		userRoles2.add(new UserRole(user2, role2));

		userService.createUser(user2, userRoles2);
	}

}