package com.talent.graph.notification_service.service.impl;

import com.talent.graph.notification_service.dto.EventNotificationRequestDTO;
import com.talent.graph.notification_service.dto.NotificationRequestDTO;
import com.talent.graph.notification_service.dto.NotificationResponseDTO;
import com.talent.graph.notification_service.mapper.NotificationMapper;
import com.talent.graph.notification_service.model.Notification;
import com.talent.graph.notification_service.model.NotificationStatus;
import com.talent.graph.notification_service.repository.NotificationRepository;
import com.talent.graph.notification_service.service.EmailService;
import com.talent.graph.notification_service.service.NotificationService;
import com.talent.graph.notification_service.service.TemplateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final EmailService emailService;
    private final TemplateService templateService;
    private final NotificationPreferenceServiceImpl preferenceService;
    
    @Override
    @Transactional
    public NotificationResponseDTO createNotification(NotificationRequestDTO requestDTO) {
        log.info("Creating notification for email: {}", requestDTO.getRecipientEmail());
        
        // Create notification entity
        Notification notification = notificationMapper.toEntity(requestDTO);
        
        // Set default values
        notification.setId(UUID.randomUUID());
        notification.setStatus(NotificationStatus.PENDING);
        notification.setCreatedAt(LocalDateTime.now());
        
        // Save to database
        Notification savedNotification = notificationRepository.save(notification);
        log.info("Notification created with ID: {}", savedNotification.getId());
        
        // Send immediately if no scheduling
        if (savedNotification.getScheduledFor() == null || 
            savedNotification.getScheduledFor().isBefore(LocalDateTime.now())) {
            sendNotificationAsync(savedNotification.getId().toString());
        }
        
        return notificationMapper.toResponseDTO(savedNotification);
    }
    
    @Override
    @Transactional
    public NotificationResponseDTO createNotificationFromEvent(EventNotificationRequestDTO eventDTO) {
        log.info("Creating notification from event: {}", eventDTO.getEventType());
        
        // Check user preferences
        if (!preferenceService.isNotificationAllowed(eventDTO.getUserId(), "EMAIL", "EVENT")) {
            log.info("User {} has disabled event notifications", eventDTO.getUserId());
            return null;
        }
        
        // Process event and create notification
        Notification notification = notificationMapper.toEntity(eventDTO);
        notification.setId(UUID.randomUUID());
        notification.setStatus(NotificationStatus.PENDING);
        notification.setCreatedAt(LocalDateTime.now());
        
        Notification savedNotification = notificationRepository.save(notification);
        
        // Send immediately
        sendNotificationAsync(savedNotification.getId().toString());
        
        return notificationMapper.toResponseDTO(savedNotification);
    }
    
    @Override
    public void sendNotification(String notificationId) {
        log.info("Sending notification: {}", notificationId);
        
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found: " + notificationId));
        
        // Check if already sent
        if (notification.getStatus() == NotificationStatus.SENT || 
            notification.getStatus() == NotificationStatus.DELIVERED) {
            log.warn("Notification {} already sent", notificationId);
            return;
        }
        
        // Update status to PROCESSING
        notification.setStatus(NotificationStatus.PROCESSING);
        notificationRepository.save(notification);
        
        try {
            // Send email
            boolean sent = emailService.sendEmail(notification);
            
            if (sent) {
                notification.setStatus(NotificationStatus.SENT);
                notification.setSentAt(LocalDateTime.now());
                notification.setRetryCount(0);
                notification.setFailureReason(null);
                log.info("Notification {} sent successfully", notificationId);
            } else {
                notification.setStatus(NotificationStatus.FAILED);
                notification.setFailureReason("Email service returned failure");
                log.error("Failed to send notification: {}", notificationId);
            }
            
        } catch (Exception e) {
            notification.setStatus(NotificationStatus.FAILED);
            notification.setFailureReason(e.getMessage());
            log.error("Error sending notification {}: {}", notificationId, e.getMessage(), e);
        }
        
        // Update retry count if failed
        if (notification.getStatus() == NotificationStatus.FAILED) {
            notification.setRetryCount(notification.getRetryCount() + 1);
        }
        
        notificationRepository.save(notification);
    }
    
    @Override
    @Scheduled(fixedDelay = 60000) // Run every minute
    public void processPendingNotifications() {
        log.info("Processing pending notifications");
        
        List<Notification> pendingNotifications = notificationRepository
                .findByStatusAndScheduledForBeforeOrScheduledForIsNull(
                        NotificationStatus.PENDING, 
                        LocalDateTime.now());
        
        if (pendingNotifications.isEmpty()) {
            log.info("No pending notifications to process");
            return;
        }
        
        log.info("Found {} pending notifications", pendingNotifications.size());
        
        for (Notification notification : pendingNotifications) {
            try {
                sendNotification(notification.getId().toString());
            } catch (Exception e) {
                log.error("Error processing notification {}: {}", notification.getId(), e.getMessage());
            }
        }
    }
    
    @Override
    public void retryFailedNotifications(int maxRetries) {
        log.info("Retrying failed notifications with max retries: {}", maxRetries);
        
        List<Notification> failedNotifications = notificationRepository
                .findFailedNotificationsForRetry(maxRetries);
        
        if (failedNotifications.isEmpty()) {
            log.info("No failed notifications to retry");
            return;
        }
        
        log.info("Found {} failed notifications to retry", failedNotifications.size());
        
        for (Notification notification : failedNotifications) {
            notification.setStatus(NotificationStatus.PENDING);
            notificationRepository.save(notification);
            sendNotificationAsync(notification.getId().toString());
        }
    }
    
    @Override
    public NotificationResponseDTO getNotificationById(String id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found: " + id));
        return notificationMapper.toResponseDTO(notification);
    }
    
    @Override
    public Page<NotificationResponseDTO> getNotificationsByUser(String userId, Pageable pageable) {
        Page<Notification> notifications = notificationRepository.findByUserId(userId, pageable);
        return notifications.map(notificationMapper::toResponseDTO);
    }
    
    @Override
    public Page<NotificationResponseDTO> getNotificationsByEmail(String email, Pageable pageable) {
        Page<Notification> notifications = notificationRepository.findByRecipientEmail(email, pageable);
        return notifications.map(notificationMapper::toResponseDTO);
    }
    
    @Override
    public Page<NotificationResponseDTO> getNotificationsByStatus(NotificationStatus status, Pageable pageable) {
        Page<Notification> notifications = notificationRepository.findByStatus(status, pageable);
        return notifications.map(notificationMapper::toResponseDTO);
    }
    
    @Override
    public Page<NotificationResponseDTO> searchNotifications(
            String recipientEmail,
            NotificationStatus status,
            String eventType,
            String resourceType,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable) {
        
        if (startDate == null) {
            startDate = LocalDateTime.now().minusDays(30);
        }
        if (endDate == null) {
            endDate = LocalDateTime.now();
        }
        
        Page<Notification> notifications = notificationRepository.searchNotifications(
                recipientEmail, status, eventType, resourceType, startDate, endDate, pageable);
        
        return notifications.map(notificationMapper::toResponseDTO);
    }
    
    @Override
    @Transactional
    public void updateNotificationStatus(String notificationId, NotificationStatus status, String failureReason) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found: " + notificationId));
        
        notification.setStatus(status);
        if (failureReason != null) {
            notification.setFailureReason(failureReason);
        }
        
        if (status == NotificationStatus.SENT) {
            notification.setSentAt(LocalDateTime.now());
        }
        
        notificationRepository.save(notification);
        log.info("Updated notification {} status to {}", notificationId, status);
    }
    
    @Override
    @Transactional
    public void cancelNotification(String notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found: " + notificationId));
        
        if (notification.getStatus() == NotificationStatus.PENDING || 
            notification.getStatus() == NotificationStatus.SCHEDULED) {
            notification.setStatus(NotificationStatus.CANCELLED);
            notificationRepository.save(notification);
            log.info("Cancelled notification: {}", notificationId);
        } else {
            throw new RuntimeException("Cannot cancel notification in status: " + notification.getStatus());
        }
    }
    
    @Override
    public Map<String, Long> getNotificationStatistics(LocalDateTime since) {
        List<Object[]> stats = notificationRepository.countNotificationsByStatusSince(since);
        
        return stats.stream()
                .collect(Collectors.toMap(
                        arr -> arr[0].toString(),
                        arr -> (Long) arr[1]
                ));
    }
    
    @Override
    public boolean hasUnreadNotifications(String userId) {
        List<Notification> unread = notificationRepository
                .findByUserIdAndStatus(userId, NotificationStatus.SENT);
        return !unread.isEmpty();
    }
    
    // Helper method for async sending
    private void sendNotificationAsync(String notificationId) {
        // In production, use @Async or message queue
        new Thread(() -> {
            try {
                sendNotification(notificationId);
            } catch (Exception e) {
                log.error("Async sending failed for {}: {}", notificationId, e.getMessage());
            }
        }).start();
    }
}