package com.talent.graph.notification_service.repository;

import com.talent.graph.notification_service.model.NotificationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationLogRepository extends MongoRepository<NotificationLog, String> {

    // Find logs by notification ID
    List<NotificationLog> findByNotificationId(String notificationId);

    // Find logs by notification template ID
    List<NotificationLog> findByNotificationTemplateId(String notificationTemplateId);

    // Find logs by recipient
    List<NotificationLog> findByRecipient(String recipient);

    // Find logs by action type
    List<NotificationLog> findByAction(String action);

    // Find logs by provider (e.g., AWS SES, Twilio)
    List<NotificationLog> findByProvider(String provider);

    // Find logs by response code
    List<NotificationLog> findByResponseCode(String responseCode);

    // Find logs within time range
    List<NotificationLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    // Find logs by sender service
    List<NotificationLog> findBySenderService(String senderService);

    // Count logs by action
    long countByAction(String action);

    // Paginated queries
    Page<NotificationLog> findAll(Pageable pageable);

    Page<NotificationLog> findByNotificationId(String notificationId, Pageable pageable);

    // Custom query for complex searches
    @Query("{'timestamp': {$gte: ?0, $lte: ?1}, 'action': ?2}")
    List<NotificationLog> findLogsByTimeRangeAndAction(
            LocalDateTime start,
            LocalDateTime end,
            String action);

    // Find logs with error responses
    @Query("{'responseCode': {$regex: '^[45]', $options: 'i'}}")
    List<NotificationLog> findErrorLogs();

    // Find latest logs for a notification
    List<NotificationLog> findByNotificationIdOrderByTimestampDesc(String notificationId);
}