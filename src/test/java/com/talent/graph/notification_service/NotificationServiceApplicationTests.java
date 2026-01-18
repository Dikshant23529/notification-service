package com.talent.graph.notification_service;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import jakarta.mail.internet.MimeMessage;

@SpringBootTest
class NotificationServiceApplicationTests {

	private JavaMailSender emailSender; 


	@Test
	void contextLoads() {

	}

	@Test
	void testSimpleMailMessage() {
		System.out.println("Testing SimpleMailMessage");

		MimeMessage mailMessage = emailSender.createMimeMessage();
		mailMessage.setFrom("dikshantlather0012@gmail.com");
		mailMessage.setRecipient(,"latherdikshant0@gmail.com");
		mailMessage.setSubject("Test Notification");
		mailMessage.setText("This is a test notification.");

		emailSender.send(mailMessage);

	}

}
