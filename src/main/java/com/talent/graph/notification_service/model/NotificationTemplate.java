package com.talent.graph.notification_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "notification_template")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    
    // For different channels
    private String emailSubject;
    private String emailBody;
    private String smsBody;
    private String pushNotificationTitle;
    private String pushNotificationBody;
    private String internalNotificationContent;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> variables; // e.g., {{userName}}, {{resourceName}}
    private NotificationType type; // INFO, SUCCESS, WARNING, ERROR
    private String category; // "ResourceCreation", "SystemAlert", "UserAction"
    private String language;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}