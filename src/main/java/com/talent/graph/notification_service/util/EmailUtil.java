// package com.talent.graph.notification_service.util;

// import java.util.Properties;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.env.Environment;
// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.mail.javamail.JavaMailSenderImpl;

// @Configuration
// public class EmailUtil {

//     @Autowired
//     private Environment env;

//     public JavaMailSender javaMailSenderImplCustom(SimpleMailMessage mailMessage) {
        
//         JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
//         javaMailSenderImpl.setHost(env.getProperty("spring.mail.host"));
//         javaMailSenderImpl.setPort(Integer.parseInt(env.getProperty("spring.mail.port")));
//         javaMailSenderImpl.setUsername(env.getProperty("spring.mail.username"));
//         javaMailSenderImpl.setPassword(env.getProperty("spring.mail.password"));
        

//         Properties props = javaMailSenderImpl.getJavaMailProperties();
//         props.put("mail.transport.protocol", "smtp");
//         props.put("mail.smtp.auth", "true");
//         props.put("mail.smtp.starttls.enable", "true");
//         props.put("mail.smtp.timeout", "5000"); 
//         props.put("mail.debug", "false");

//         javaMailSenderImpl.setJavaMailProperties(props);

//         javaMailSenderImpl.send(mailMessage);

//         return javaMailSenderImpl;

//     }

// }
