package com.talent.graph.notification_service.service;

import com.talent.graph.notification_service.dto.EventNotificationRequestDTO;
import com.talent.graph.notification_service.dto.NotificationRequestDTO;
import com.talent.graph.notification_service.dto.NotificationResponseDTO;
import com.talent.graph.notification_service.model.NotificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface NotificationService {
    
    // Create notification from direct request
    NotificationResponseDTO createNotification(NotificationRequestDTO requestDTO);
    
    // Create notification from event (from other services)
    NotificationResponseDTO createNotificationFromEvent(EventNotificationRequestDTO eventDTO);
    
    // Send a specific notification
    void sendNotification(String notificationId);
    
    // Process pending notifications (batch processing)
    void processPendingNotifications();
    
    // Retry failed notifications
    void retryFailedNotifications(int maxRetries);
    
    // Get notification by ID
    NotificationResponseDTO getNotificationById(String id);
    
    // Get notifications by user
    Page<NotificationResponseDTO> getNotificationsByUser(String userId, Pageable pageable);
    
    // Get notifications by recipient email
    Page<NotificationResponseDTO> getNotificationsByEmail(String email, Pageable pageable);
    
    // Get notifications by status
    Page<NotificationResponseDTO> getNotificationsByStatus(NotificationStatus status, Pageable pageable);
    
    // Search notifications with filters
    Page<NotificationResponseDTO> searchNotifications(
            String recipientEmail,
            NotificationStatus status,
            String eventType,
            String resourceType,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable);
    
    // Update notification status
    void updateNotificationStatus(String notificationId, NotificationStatus status, String failureReason);
    
    // Cancel a scheduled notification
    void cancelNotification(String notificationId);
    
    // Get notification statistics
    Map<String, Long> getNotificationStatistics(LocalDateTime since);
    
    // Check if user has unread notifications
    boolean hasUnreadNotifications(String userId);
}