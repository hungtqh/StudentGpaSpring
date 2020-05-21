package com.gpa.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gpa.domain.Student;
import com.gpa.domain.User;
import com.gpa.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	
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
	public String showLogin() {
		return "login";
	}

}
