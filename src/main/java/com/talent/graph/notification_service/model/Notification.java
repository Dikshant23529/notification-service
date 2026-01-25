package com.talent.graph.notification_service.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;


@Document 
@Data
public class Notification {

    @Id
    private String id;

    @Indexed
    @Field("user_id")
    private String userId;

    @Indexed
    @Field("recipient_email")
    private String recipientEmail;

    @Field("subject")
    private String subject;

    @Field("notificationTemplateId")
    private String notificationTemplateId;

    private NotificationType notificationType = NotificationType.EMAIL;

    @Field("status")
    private NotificationStatus status = NotificationStatus.PENDING;

    private NotificationPriority notificationPriority = NotificationPriority.LOW;

    @CreatedDate
    @Field("event_initiated")
    private LocalDateTime eventInitiated;

    private Integer retryCount = 0;

    private LocalDateTime eventProcessed;

    private String remarks;

}