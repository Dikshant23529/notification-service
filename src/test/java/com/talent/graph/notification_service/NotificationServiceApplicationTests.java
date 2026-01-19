package com.talent.graph.notification_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class NotificationServiceApplicationTests {

	@Autowired
	private JavaMailSender emailSender;

	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceApplicationTests.class);

	@Autowired
	private Environment env;

	@Test
	void contextLoads() {

	}

	@Test
	void testEnvVariables() {

		logger.info("=== Testing Environment Variables ===");

		String host = env.getProperty("SMTP_HOST");
		String username = env.getProperty("SMTP_USERNAME");
		String port = env.getProperty("SMTP_PORT");

		logger.info("SMTP_HOST: {}", host);
		logger.info("SMTP_USERNAME: {}", username);
		logger.info("SMTP_PORT: {}", port);

	}

	@Test
	void testSimpleMailMessage() {
		System.out.println("Testing SimpleMailMessage");

		logger.info("=== Testing Environment Variables ===");

		String host = env.getProperty("SMTP_HOST");
		String username = env.getProperty("SMTP_USERNAME");
		String port = env.getProperty("SMTP_PORT");

		logger.info("SMTP_HOST: {}", host);
		logger.info("SMTP_USERNAME: {}", username);
		logger.info("SMTP_PORT: {}", port);

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo("latherdikshant0@gmail.com");
		mailMessage.setFrom("dikshantlather0012@gmail.com");
		mailMessage.setSubject("Test Notification Service");
		mailMessage.setText("This is a test email from Notification Service.");

		emailSender.send(mailMessage);

	}

}
