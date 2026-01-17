//package com.talent.graph.notification_service.service;
//
//import com.talent.graph.notification_service.model.Notification;
//
//import java.util.List;
//import java.util.Map;
//
//public interface EmailService {
//
//    // Send email for a notification
//    boolean sendEmail(Notification notification);
//
//    // Send bulk emails
//    void sendBulkEmails(List<Notification> notifications);
//
//    // Validate email format
//    boolean isValidEmail(String email);
//
//    // Check email server connectivity
//    boolean testConnection();
//
//    // Process email template with variables
//    String processTemplate(String template, Map<String, Object> variables);
//}