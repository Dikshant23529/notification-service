package com.talent.graph.notification_service.service.impl;

import com.talent.graph.notification_service.model.Notification;
import com.talent.graph.notification_service.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    
    private final JavaMailSender mailSender;
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    
    @Override
    public boolean sendEmail(Notification notification) {
        try {
            log.info("Sending email to: {}", notification.getRecipientEmail());
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(notification.getRecipientEmail());
            helper.setSubject(notification.getSubject());
            helper.setText(notification.getBody(), true); // true = HTML content
            
            // Set from address (configured in properties)
            // helper.setFrom("noreply@yourapp.com");
            
            mailSender.send(message);
            
            log.info("Email sent successfully to: {}", notification.getRecipientEmail());
            return true;
            
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", notification.getRecipientEmail(), e.getMessage());
            return false;
        }
    }
    
    @Override
    public void sendBulkEmails(List<Notification> notifications) {
        log.info("Sending {} bulk emails", notifications.size());
        
        int successCount = 0;
        int failureCount = 0;
        
        for (Notification notification : notifications) {
            try {
                boolean sent = sendEmail(notification);
                if (sent) {
                    successCount++;
                } else {
                    failureCount++;
                }
            } catch (Exception e) {
                log.error("Error sending email to {}: {}", 
                        notification.getRecipientEmail(), e.getMessage());
                failureCount++;
            }
        }
        
        log.info("Bulk email sending completed. Success: {}, Failed: {}", 
                successCount, failureCount);
    }
    
    @Override
    public boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return Pattern.compile(EMAIL_PATTERN)
                .matcher(email)
                .matches();
    }
    
    @Override
    public boolean testConnection() {
        try {
            mailSender.testConnection();
            log.info("Email server connection test successful");
            return true;
        } catch (Exception e) {
            log.error("Email server connection test failed: {}", e.getMessage());
            return false;
        }
    }
    
    @Override
    public String processTemplate(String template, Map<String, Object> variables) {
        String processed = template;
        
        if (variables != null) {
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                String placeholder = "{{" + entry.getKey() + "}}";
                String value = entry.getValue() != null ? entry.getValue().toString() : "";
                processed = processed.replace(placeholder, value);
            }
        }
        
        return processed;
    }
}