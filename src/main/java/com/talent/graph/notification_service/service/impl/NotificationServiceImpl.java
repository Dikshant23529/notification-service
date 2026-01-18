package com.talent.graph.notification_service.service.impl;

import com.talent.graph.notification_service.model.Notification;
import com.talent.graph.notification_service.model.NotificationStatus;
import com.talent.graph.notification_service.repository.NotificationLogRepository;
import com.talent.graph.notification_service.repository.NotificationRepository;
import com.talent.graph.notification_service.repository.NotificationTemplateRepository;
import com.talent.graph.notification_service.service.NotificationService;
import com.talent.graph.notification_service.util.EmailUtil;

import jakarta.mail.Session;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
 
    private NotificationRepository notificationRepository;

    private NotificationTemplateRepository notificationTemplateRepository;

    private NotificationLogRepository notificationLogRepository;

    @Autowired
    private Session emailSession;

    public Notification initiateNotification(String userId, String recipientEmail, String templateName) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setRecipientEmail(recipientEmail);
        notification.setNotificationTemplateId(templateName);
        notification.setStatus(NotificationStatus.PENDING);
        notification.setEventInitiated(LocalDateTime.now());

        return notificationRepository.save(notification);
    }

    @Transactional
    public void processPendingNotifications() {
        LocalDateTime now = LocalDateTime.now();
        List<Notification> pendingNotifications = notificationRepository.findByStatus(NotificationStatus.PENDING);
        for (Notification notification : pendingNotifications) {
            try {
                sendNotification(notification);
                notification.setStatus(NotificationStatus.SENT);
                createNotificationLog(notification, "Notification sent successfully", true);
            } catch (Exception e) {
                log.error("Failed to send notification: {}", e.getMessage());
                notification.setStatus(NotificationStatus.FAILED);
                createNotificationLog(notification, e.getMessage(), false);
            }
            notificationRepository.save(notification);
        }
    }

    @Override
    public void sendNotification(Notification notification) throws Exception {
    
        boolean sent = EmailUtil.sendEmail(emailSession,"12@gmail.com", notification.getRecipientEmail(), notification.getSubject(), notification.getNotificationTemplateId(),false);
    
    if (!sent) {
        
    }

    }

    @Override
    public void createNotificationLog(Notification notification, String responseMessage, boolean success) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createNotificationLog'");
    }

}