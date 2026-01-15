package com.talent.graph.notification_service.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;


@Document(collation = "notifications")
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

    @Field("body")
    private String body;

    @Field("status")
    private NotificationStatus status = NotificationStatus.PENDING;

    @Field("retry_count")
    private Integer retryCount = 0;

    @Field("failure_reason")
    private String failureReason;

    @Field("send_at")
    private LocalDateTime sendAt;

    @Field("refrence_id")
    private String refrenceId;

    @Field("resource_type")
    private String resourceType;

    @Field("event_type")
    private String eventType;

    @Field("metadata")
    private Map<String, Object> metadata;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;

}