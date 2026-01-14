package com.talent.graph.notification_service.model;

import java.time.LocalDateTime;
import java.util.Map;

public class NotificationPreference {
    private String id;
    private String userId;
    
    // Channel preferences
    private boolean emailEnabled;
    private boolean smsEnabled;
    private boolean pushEnabled;
    private boolean internalEnabled;
    
    // Category preferences
    private Map<String, ChannelPreference> categoryPreferences;
    // e.g., "ResourceCreation" -> {email: true, sms: false}
    
    // Quiet hours
    private String quietHoursStart;
    private String quietHoursEnd;
    private String timezone;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}