package com.gpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentGpaApplication { //implements CommandLineRunner {

//	@Autowired
//	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(StudentGpaApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		User user1 = new User();
//		user1.setUsername("B17DCCN123");
//		user1.setPassword(SecurityUtility.passwordEncoder().encode("12345"));
//		user1.setEmail("hungict100@gmail.com");
//
//		Role role1 = new Role();
//		role1.setRoleId(1);
//		role1.setName("ROLE_STUDENT");
//
//		Set<UserRole> userRoles = new HashSet<>();
//		userRoles.add(new UserRole(user1, role1));
//
//		userService.createUser(user1, userRoles);
//	}

}
