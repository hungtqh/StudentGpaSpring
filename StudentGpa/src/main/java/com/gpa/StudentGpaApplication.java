package com.gpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
