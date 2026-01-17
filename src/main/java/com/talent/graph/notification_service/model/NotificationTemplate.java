package com.talent.graph.notification_service.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collation = "notifications")
@Data
public class NotificationTemplate {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String description;

    private String subjectTemplate;

    private String bodyTemplate;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}