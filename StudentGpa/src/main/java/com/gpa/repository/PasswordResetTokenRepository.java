package com.gpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gpa.domain.PasswordResetToken;
import com.gpa.domain.User;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{
	
	PasswordResetToken findByToken(String token);
	
	PasswordResetToken findByUser(User user);
}
