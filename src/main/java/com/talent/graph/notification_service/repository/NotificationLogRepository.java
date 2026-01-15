//package com.talent.graph.notification_service.repository;
//
//import com.talent.graph.notification_service.model.NotificationLog;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Repository
//public interface NotificationLogRepository extends JpaRepository<NotificationLog, String> {
//
//    // Find logs by notification ID
//    List<NotificationLog> findByNotificationId(String notificationId);
//
//    // Find logs by user ID
//    Page<NotificationLog> findByUserId(String userId, Pageable pageable);
//
//    // Find logs by action type
//    List<NotificationLog> findByAction(String action);
//
//    // Find logs within time range
//    List<NotificationLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
//
//    // Find logs for notification with specific action
//    @Query("SELECT l FROM NotificationLog l WHERE l.notificationId = :notificationId AND l.action = :action ORDER BY l.timestamp DESC")
//    List<NotificationLog> findLogsByNotificationAndAction(
//            @Param("notificationId") String notificationId,
//            @Param("action") String action);
//
//    // Count logs by action for statistics
//    @Query("SELECT l.action, COUNT(l) FROM NotificationLog l WHERE l.timestamp >= :since GROUP BY l.action")
//    List<Object[]> countLogsByActionSince(@Param("since") LocalDateTime since);
//}