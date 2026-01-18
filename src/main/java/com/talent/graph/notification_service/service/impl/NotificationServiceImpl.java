package com.talent.graph.notification_service.service.impl;

import com.talent.graph.notification_service.model.Notification;
import com.talent.graph.notification_service.model.NotificationStatus;
import com.talent.graph.notification_service.repository.NotificationRepository;
import com.talent.graph.notification_service.service.NotificationService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
 
    private NotificationRepository notificationRepository;

    public Notification initiateNotification(String userId, String recipientEmail, String templateName) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setRecipientEmail(recipientEmail);
        notification.setNotificationTemplateId(templateName);
        notification.setStatus(NotificationStatus.PENDING);
        notification.setEventInitiated(LocalDateTime.now());

        return notificationRepository.save(notification);
    }

    public void processPendingNotifications() {
        // Implementation for processing pending notifications
    }

    @Override
    public void sendNotification(Notification notification) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendNotification'");
    }

    @Override
    public void createNotificationLog(Notification notification, String responseMessage, boolean success) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createNotificationLog'");
    }

}