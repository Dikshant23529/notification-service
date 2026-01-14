package com.talent.graph.notification_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class NotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
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