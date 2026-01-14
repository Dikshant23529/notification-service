package com.talent.graph.notification_service.model;

import java.time.LocalDateTime;

public class NotificationLog {
    private String id;
    private String notificationId;
    private String userId;
    private NotificationType notificationType;
    private String channel; // EMAIL, SMS, PUSH, INTERNAL
    
    private String action; // SENT, DELIVERED, READ, CLICKED, FAILED
    private String details;
    private String providerResponse; // Response from email/SMS provider
    
    private LocalDateTime timestamp;
    private String ipAddress; // For tracking clicks
    private String userAgent;
}