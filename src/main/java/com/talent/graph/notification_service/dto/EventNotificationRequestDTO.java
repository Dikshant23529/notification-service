package com.talent.graph.notification_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventNotificationRequestDTO {
    
    @NotBlank(message = "Event type is required")
    private String eventType; // "RESOURCE_CREATED", "TASK_COMPLETED"
    
    @NotBlank(message = "User email is required")
    @Email(message = "Invalid email format")
    private String userEmail;
    
    private String userId;
    private String userName;
    
    @NotBlank(message = "Resource ID is required")
    private String resourceId;
    
    private String resourceName;
    private String resourceType;
    
    private Map<String, Object> additionalData;
    
    private String correlationId;
    
    // Getters and setters
}