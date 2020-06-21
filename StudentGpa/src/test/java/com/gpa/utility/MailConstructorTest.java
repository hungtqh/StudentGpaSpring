package com.gpa.utility;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.core.env.Environment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;

import com.gpa.domain.User;

@SpringBootTest
class MailConstructorTest {
	
	@Autowired
	private Environment env;

	@Autowired
	private MailConstructor mailConstructor;
	
	@Test
	void testConstructResetTokenEmail() {
		String token = "ABCDEF123";
		String appUrl = "http://localhost:8080";
		User user = new User();
		user.setEmail("test@gmail.com");
		String password = SecurityUtility.randomPassword();
		
		SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, token, user, password);

		
		assertTrue(
				  newEmail.getTo()[0].equals("test@gmail.com") &&
				  newEmail.getSubject().equals("PTIT - Quên mật khẩu") &&
				  newEmail.getText().equals(appUrl + "/forgetPassword?token=" + token +
						"\nNhấn vào đây để thay đổi mật khẩu. Mật khẩu của bạn là "
						+ password) &&
				  newEmail.getFrom().equals(env.getProperty("support-email"))
									 
				);
	}

}
