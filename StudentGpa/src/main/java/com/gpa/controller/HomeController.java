package com.gpa.controller;

import java.security.Principal;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gpa.domain.PasswordResetToken;
import com.gpa.domain.Student;
import com.gpa.domain.User;
import com.gpa.service.UserSecurityService;
import com.gpa.service.UserService;
import com.gpa.utility.MailConstructor;
import com.gpa.utility.SecurityUtility;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	@Autowired
	private MailConstructor mailConstructor;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@GetMapping("/home")
	public String home(Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		
		Student student = user.getStudent();
		
		model.addAttribute("student", student);
		
		return "home";
	}
	
	@GetMapping("/")
	public String index() {
		return "redirect:/home";
	}
	
	@GetMapping("/login")
	public String showLogin(Model model) {
		model.addAttribute("classActiveLogin", true);
		
		return "login";
	}

	@PostMapping("/forgetPassword")
	public String forgetPassword(HttpServletRequest request, @ModelAttribute("email") String userEmail, Model model) {

		User user = userService.findByEmail(userEmail);
		model.addAttribute("classActiveForgetPassword", true);

		if (user == null) {
			model.addAttribute("emailNotExists", true);

			return "login";
		}

		String password = SecurityUtility.randomPassword();
		String encryptPassword = SecurityUtility.passwordEncoder().encode(password);

		user.setPassword(encryptPassword);

		userService.save(user); // update new password

		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);

		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

		SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user,
				password);

		mailSender.send(newEmail);

		model.addAttribute("forgetPasswordEmailSent", true);

		return "login";
	}
	
	@GetMapping("/changePassword")
	public String showChangePassword(Model model, Principal principal) {
		
		User user = userService.findByUsername(principal.getName());
		
		Student student = user.getStudent();
		
		model.addAttribute("user", user);
		model.addAttribute("student", student);
		model.addAttribute("classActiveEdit", true);
		
		return "home";
	}
	
	@GetMapping("/forgetPassword")
	public String newUser(Model model, Locale locale, @RequestParam("token") String token) {

		PasswordResetToken passToken = userService.getPasswordResetToken(token);

		if (passToken == null) {
			String message = "Token không hợp lệ.";
			model.addAttribute("message", message);
			return "redirect:/badRequest";
		}

		User user = passToken.getUser();
		String username = user.getUsername();

		// set current login session to the user and the user will have permission to
		// access all required login request
		UserDetails userDetails = userSecurityService.loadUserByUsername(username);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		Student student = user.getStudent();
		
		model.addAttribute("student", student);
		model.addAttribute("user", user);
		
		model.addAttribute("classActiveEdit", true);
		
		return "home";
	}
	
	@PostMapping("/updateStudentInfo")
	public String updateUserInfo(@ModelAttribute("user") User user,
			@ModelAttribute("newPassword") String newPassword, Model model) throws Exception {
		
		User currentUser = userService.findbyId(user.getId());

		if (currentUser == null) {
			throw new Exception("User not found");
		}

		/* update password */
		if (newPassword != null && !newPassword.isEmpty()) {
			BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
			String dbPassword = currentUser.getPassword();
			
			// inputed old password is correct
			if (passwordEncoder.matches(user.getPassword(), dbPassword)) { // (rawPassword, encodedPassword)
				// the new password is the same as the old password
				if (passwordEncoder.matches(newPassword, dbPassword)) {
					model.addAttribute("student", currentUser.getStudent());
					model.addAttribute("samePassword", true);
					model.addAttribute("classActiveEdit", true);
					return "home";
				} else {
					currentUser.setPassword(passwordEncoder.encode(newPassword));
				}
			} else {
				model.addAttribute("student", currentUser.getStudent());
				model.addAttribute("incorrectPassword", true);
				model.addAttribute("classActiveEdit", true);
				return "home";
			}
		}
		
		userService.save(currentUser);

		model.addAttribute("student", currentUser.getStudent());
		model.addAttribute("updateSuccess", true);
		model.addAttribute("classActiveEdit", true);

		UserDetails userDetails = userSecurityService.loadUserByUsername(currentUser.getUsername());

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return "home";
	}
}
