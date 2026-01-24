package com.talent.graph.notification_service.service.impl;

import com.talent.graph.notification_service.model.Notification;
import com.talent.graph.notification_service.model.NotificationStatus;
import com.talent.graph.notification_service.model.WebhookRequest;
import com.talent.graph.notification_service.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
public class NotificationServiceImpl {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * CREATE NOTIFICATION RECORD
     */
    public Notification initiateNotification(String userId, String recipientEmail, String templateName) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setRecipientEmail(recipientEmail);
        notification.setNotificationTemplateId(templateName);
        notification.setStatus(NotificationStatus.PENDING);
        notification.setEventInitiated(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    /**
     * 🔥 SCHEDULER METHOD - RUNS AUTOMATICALLY
     * Processes pending notifications every 30 seconds
     */
    @Scheduled(fixedDelay = 30000) // Runs every 30 seconds
    @Transactional
    public void processPendingNotificationsScheduled() {
        log.info("⏰ SCHEDULER: Starting notification processing...");

        // Get pending notifications
        List<Notification> pendingNotifications = notificationRepository
                .findByStatus(NotificationStatus.PENDING);

        log.info("⏰ SCHEDULER: Found {} pending notifications", pendingNotifications.size());

        if (pendingNotifications.isEmpty()) {
            log.info("⏰ SCHEDULER: No pending notifications found");
            return;
        }

        // Process each notification
        int successCount = 0;
        int failedCount = 0;

        for (Notification notification : pendingNotifications) {
            try {
                log.info("⏰ SCHEDULER: Processing notification ID: {} for user: {}",
                        notification.getId(), notification.getUserId());

                // Send email
                sendNotification(notification);

                // Update status
                notification.setStatus(NotificationStatus.SENT);
                notification.setEventProcessed(LocalDateTime.now());
                notification.setRemarks("Sent via scheduled job");

                notificationRepository.save(notification);
                successCount++;

                log.info("✅ SCHEDULER: Successfully sent notification to: {}",
                        notification.getRecipientEmail());

            } catch (Exception e) {
                log.error("❌ SCHEDULER: Failed to send notification ID: {}",
                        notification.getId(), e);

                notification.setStatus(NotificationStatus.FAILED);
                notification.setEventProcessed(LocalDateTime.now());
                notification.setRemarks("Failed: " + e.getMessage());

                notificationRepository.save(notification);
                failedCount++;
            }
        }

        log.info("⏰ SCHEDULER: Completed! Success: {}, Failed: {}",
                successCount, failedCount);
    }

    /**
     * 🔥 RETRY FAILED NOTIFICATIONS SCHEDULER
     * Retries failed notifications every 5 minutes (max 3 retries)
     */
    @Scheduled(fixedDelay = 300000) // Every 5 minutes
    @Transactional
    public void retryFailedNotifications() {
        log.info("🔄 RETRY SCHEDULER: Checking for failed notifications...");

        // Get failed notifications (with less than 3 retries)
        List<Notification> failedNotifications = notificationRepository
                .findByStatusAndRetryCountLessThan(NotificationStatus.FAILED, 3);

        log.info("🔄 RETRY SCHEDULER: Found {} failed notifications to retry",
                failedNotifications.size());

        for (Notification notification : failedNotifications) {
            try {
                log.info("🔄 RETRY SCHEDULER: Retrying notification ID: {}", notification.getId());

                // Increment retry count
                int currentRetries = notification.getRetryCount() != null ?
                        notification.getRetryCount() : 0;
                notification.setRetryCount(currentRetries + 1);

                // Try to send again
                sendNotification(notification);

                // Update status if successful
                notification.setStatus(NotificationStatus.SENT);
                notification.setEventProcessed(LocalDateTime.now());
                notification.setRemarks("Sent after " + notification.getRetryCount() + " retries");

                notificationRepository.save(notification);

                log.info("✅ RETRY SCHEDULER: Successfully resent notification ID: {}",
                        notification.getId());

            } catch (Exception e) {
                log.error("❌ RETRY SCHEDULER: Retry failed for notification ID: {}",
                        notification.getId(), e);

                notification.setRemarks("Retry " + notification.getRetryCount() +
                        " failed: " + e.getMessage());
                notificationRepository.save(notification);
            }
        }
    }

    /**
     * 🔥 CLEANUP SCHEDULER
     * Cleans up old notifications daily at 2 AM
     */
    @Scheduled(cron = "0 0 2 * * ?") // Daily at 2 AM
    @Transactional
    public void cleanupOldNotifications() {
        log.info("🧹 CLEANUP SCHEDULER: Starting cleanup...");

        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minus(30, ChronoUnit.DAYS);

        // Delete sent notifications older than 30 days
        List<Notification> oldNotifications = notificationRepository
                .findByStatusAndEventProcessedBefore(NotificationStatus.SENT, thirtyDaysAgo);

        // Also delete permanently failed notifications (failed with max retries)
        List<Notification> permanentlyFailed = notificationRepository
                .findByStatusAndRetryCountGreaterThanEqualAndEventProcessedBefore(
                        NotificationStatus.FAILED, 3, thirtyDaysAgo);

        oldNotifications.addAll(permanentlyFailed);

        if (!oldNotifications.isEmpty()) {
            notificationRepository.deleteAll(oldNotifications);
            log.info("🧹 CLEANUP SCHEDULER: Deleted {} old notifications",
                    oldNotifications.size());
        } else {
            log.info("🧹 CLEANUP SCHEDULER: No old notifications to delete");
        }
    }

    /**
     * 📧 SEND EMAIL METHOD
     * (Your existing method - kept as is)
     */
    public void sendNotification(Notification notification) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("notification@gmail.com");
            mailMessage.setTo(notification.getRecipientEmail());
            mailMessage.setSubject("Notification Alert");
            mailMessage.setText("Hello! This is a notification from our system.");

            mailSender.send(mailMessage);

            log.info("📧 Email sent successfully to: {}", notification.getRecipientEmail());

        } catch (Exception e) {
            log.error("❌ Failed to send email to: {}", notification.getRecipientEmail(), e);
            throw new RuntimeException("Email sending failed: " + e.getMessage(), e);
        }
    }

    /**
     * MANUAL TRIGGER METHOD (for testing/backup)
     * Keep your existing method but rename to avoid conflict
     */
    @Transactional
    public void processPendingNotificationsManual() {
        log.info("👨‍💻 MANUAL: Processing pending notifications...");
        processPendingNotificationsScheduled(); // Reuse the same logic
    }

    /**
     * SEND NOTIFICATION VIA WEBHOOK
     * (Your existing method - kept as is)
     */
    public void sendNotification(WebhookRequest webhookRequest) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("notification@gmail.com");
            mailMessage.setTo(webhookRequest.getEmail());
            mailMessage.setSubject("Notification");
            mailMessage.setText("This is a notification.");
            mailSender.send(mailMessage);
        } catch (Exception e) {
            log.error("Failed to send notification via webhook", e);
        }
    }


    public void createNotificationLog(Notification notification, String responseMessage, boolean success) {
        // TODO: Implement if needed
        throw new UnsupportedOperationException("Unimplemented method 'createNotificationLog'");
    }

    @Transactional
    public void processPendingNotifications() {



        LocalDateTime now = LocalDateTime.now();
        List<Notification> pendingNotifications = notificationRepository.findByStatus(NotificationStatus.PENDING);
        for (Notification notification : pendingNotifications) {
            try {
                sendNotification(notification);
                notification.setStatus(NotificationStatus.SENT);
            } catch (Exception e) {
                log.error("Failed to send notification: {}", e.getMessage());
                notification.setStatus(NotificationStatus.FAILED);
            }
            notificationRepository.save(notification);
        }
    }


}