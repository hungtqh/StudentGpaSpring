package com.gpa.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.gpa.domain.User;

@Component
public class MailConstructor {

	@Autowired
	private Environment env;

	public SimpleMailMessage constructResetTokenEmail(String contextPath, String token, User user,
			String password) {
		String url = contextPath + "/forgetPassword?token=" + token;
		String message = "\nNhấn vào đây để thay đổi mật khẩu. Mật khẩu của bạn là "
				+ password;
		
		SimpleMailMessage email = new SimpleMailMessage();
		
		email.setTo(user.getEmail());
		email.setSubject("PTIT - Quên mật khẩu");
		email.setText(url + message);
		email.setFrom(env.getProperty("support-email"));
		
		return email;
	}
}
