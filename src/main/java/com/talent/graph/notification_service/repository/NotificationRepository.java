//package com.talent.graph.notification_service.repository;
//
//import com.talent.graph.notification_service.model.Notification;
//import com.talent.graph.notification_service.model.NotificationStatus;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface NotificationRepository extends JpaRepository<Notification, String> {
//
//    // Find notifications by recipient email
//    Page<Notification> findByRecipientEmail(String recipientEmail, Pageable pageable);
//
//    // Find notifications by status
//    List<Notification> findByStatus(NotificationStatus status);
//
//    // Find notifications by status with pagination
//    Page<Notification> findByStatus(NotificationStatus status, Pageable pageable);
//
//    // Find pending notifications that need to be sent
//    List<Notification> findByStatusAndScheduledForBeforeOrScheduledForIsNull(
//            NotificationStatus status,
//            LocalDateTime now);
//
//    // Find notifications by reference ID (for tracing)
//    List<Notification> findByReferenceId(String referenceId);
//
//    // Find notifications by event type
//    Page<Notification> findByEventType(String eventType, Pageable pageable);
//
//    // Find notifications by recipient and status
//    List<Notification> findByRecipientEmailAndStatus(String recipientEmail, NotificationStatus status);
//
//    // Find notifications created within a time range
//    List<Notification> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
//
//    // Count notifications by status
//    long countByStatus(NotificationStatus status);
//
//    // Custom query for complex searches
//    @Query("SELECT n FROM Notification n WHERE " +
//           "(:recipientEmail IS NULL OR n.recipientEmail = :recipientEmail) AND " +
//           "(:status IS NULL OR n.status = :status) AND " +
//           "(:eventType IS NULL OR n.eventType = :eventType) AND " +
//           "(:resourceType IS NULL OR n.resourceType = :resourceType) AND " +
//           "(n.createdAt BETWEEN :startDate AND :endDate)")
//    Page<Notification> searchNotifications(
//            @Param("recipientEmail") String recipientEmail,
//            @Param("status") NotificationStatus status,
//            @Param("eventType") String eventType,
//            @Param("resourceType") String resourceType,
//            @Param("startDate") LocalDateTime startDate,
//            @Param("endDate") LocalDateTime endDate,
//            Pageable pageable);
//
//    // Find failed notifications for retry
//    @Query("SELECT n FROM Notification n WHERE n.status = 'FAILED' AND n.retryCount < :maxRetries")
//    List<Notification> findFailedNotificationsForRetry(@Param("maxRetries") int maxRetries);
//
//    // Statistics query
//    @Query("SELECT n.status, COUNT(n) FROM Notification n WHERE n.createdAt >= :since GROUP BY n.status")
//    List<Object[]> countNotificationsByStatusSince(@Param("since") LocalDateTime since);
//}