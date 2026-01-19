package com.talent.graph.notification_service.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collation = "notification_logs")
public class NotificationLog {

    @Id
    @Indexed
    private String id;

    private String action;

    private Map<String, Object> details;

    private String provider;

    private String responseCode;

    private String responseMessage;

    private String senderService;

    private String recipient;

    private String notificationId;

    private String notificationTemplateId;

    private String ipAddress;

    private LocalDateTime timestamp;

}