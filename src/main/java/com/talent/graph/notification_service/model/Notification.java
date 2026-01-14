package com.talent.graph.notification_service.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userId; // or userEmail/username
    private List<String> recipientEmails; // For multiple recipients
    private List<String> recipientUserIds;
    
    // Notification content
    private String title;
    private String message;
    private String htmlContent; // For rich notifications

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> data; // Additional dynamic data
    
    // Metadata
    private NotificationType type; // EMAIL, SMS, PUSH, INTERNAL
    private NotificationPriority priority; // LOW, MEDIUM, HIGH, URGENT
    private String category;
    private String sourceService; // Which service generated this
    private String eventType; // "ResourceCreated", "TaskCompleted", "AlertTriggered"
    
    // Status tracking
    private NotificationStatus status; // PENDING, SENDING, SENT, FAILED, READ
    private Integer retryCount;
    private String failureReason;
    
    // Timestamps
    private LocalDateTime scheduledFor;
    private LocalDateTime sentAt;
    private LocalDateTime readAt;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    
    // Channel-specific
    private String emailReplyTo;
    private String smsSenderId;
    private String pushNotificationToken;
    
    // References
    private String referenceId; // ID of the resource this notification is about
    private String referenceType; // "Resource", "Task", "User"
    private String templateId; // If using a template
}