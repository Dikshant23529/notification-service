package com.talent.graph.notification_service.mapper.impl;

import com.talent.graph.notification_service.dto.EventNotificationRequestDTO;
import com.talent.graph.notification_service.dto.NotificationRequestDTO;
import com.talent.graph.notification_service.dto.NotificationResponseDTO;
import com.talent.graph.notification_service.mapper.NotificationMapper;
import com.talent.graph.notification_service.model.Notification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class NotificationMapperImpl implements NotificationMapper {
    
    @Override
    public Notification toEntity(NotificationRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }
        
        Notification notification = new Notification();
        notification.setRecipientEmail(requestDTO.getRecipientEmail());
        notification.setSubject(requestDTO.getSubject());
        notification.setBody(requestDTO.getBody());
        notification.setReferenceId(requestDTO.getReferenceId());
        notification.setResourceType(requestDTO.getResourceType());
        notification.setEventType(requestDTO.getEventType());
        notification.setUserId(requestDTO.getUserId()); // Make sure this field exists in DTO
        
        // Set default values
        notification.setId(UUID.randomUUID());
        notification.setCreatedAt(LocalDateTime.now());
        
        return notification;
    }
    
    @Override
    public Notification toEntity(EventNotificationRequestDTO eventDTO) {
        if (eventDTO == null) {
            return null;
        }
        
        Notification notification = new Notification();
        notification.setRecipientEmail(eventDTO.getUserEmail());
        notification.setUserId(eventDTO.getUserId());
        
        // Generate subject and body from event
        notification.setSubject(generateSubjectFromEvent(eventDTO));
        notification.setBody(generateBodyFromEvent(eventDTO));
        
        notification.setReferenceId(eventDTO.getResourceId());
        notification.setResourceType(eventDTO.getResourceType());
        notification.setEventType(eventDTO.getEventType());
        
        // Set default values
        notification.setId(UUID.randomUUID());
        notification.setCreatedAt(LocalDateTime.now());
        
        return notification;
    }
    
    @Override
    public NotificationResponseDTO toResponseDTO(Notification notification) {
        if (notification == null) {
            return null;
        }
        
        return NotificationResponseDTO.builder()
                .id(notification.getId().toString())
                .userId(notification.getUserId())
                .recipientEmail(notification.getRecipientEmail())
                .subject(notification.getSubject())
                .body(notification.getBody())
                .status(notification.getStatus() != null ? notification.getStatus().toString() : null)
                .priority(notification.getPriority() != null ? notification.getPriority().toString() : null)
                .referenceId(notification.getReferenceId())
                .resourceType(notification.getResourceType())
                .eventType(notification.getEventType())
                .sourceService(notification.getSourceService())
                .retryCount(notification.getRetryCount())
                .failureReason(notification.getFailureReason())
                .scheduledFor(notification.getScheduledFor())
                .sentAt(notification.getSentAt())
                .createdAt(notification.getCreatedAt())
                .updatedAt(notification.getUpdatedAt())
                .build();
    }
    
    private String generateSubjectFromEvent(EventNotificationRequestDTO eventDTO) {
        if (eventDTO.getEventType() == null) {
            return "Notification from System";
        }
        
        switch (eventDTO.getEventType().toUpperCase()) {
            case "RESOURCE_CREATED":
                return "Resource Creation Started";
            case "RESOURCE_CREATED_COMPLETED":
                return "Resource Created Successfully";
            case "RESOURCE_CREATION_FAILED":
                return "Resource Creation Failed";
            default:
                return "Notification from System";
        }
    }
    
    private String generateBodyFromEvent(EventNotificationRequestDTO eventDTO) {
        return String.format(
            "Hello %s,\n\nYour resource '%s' (%s) event: %s\n\nResource ID: %s\n\nAdditional Data: %s",
            eventDTO.getUserName() != null ? eventDTO.getUserName() : "User",
            eventDTO.getResourceName() != null ? eventDTO.getResourceName() : "Resource",
            eventDTO.getResourceType() != null ? eventDTO.getResourceType() : "Unknown",
            eventDTO.getEventType(),
            eventDTO.getResourceId(),
            eventDTO.getAdditionalData() != null ? eventDTO.getAdditionalData().toString() : "None"
        );
    }
}