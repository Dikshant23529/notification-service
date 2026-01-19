package com.talent.graph.notification_service.repository;

import com.talent.graph.notification_service.model.Notification;
import com.talent.graph.notification_service.model.NotificationStatus;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    List<Notification> findByUserId(String userId);

    Page<Notification> findByUserId(String userId, Pageable pageable);

    List<Notification> findByRecipientEmail(String recipientEmail);
   

    List<Notification> findByStatus(NotificationStatus status);

    List<Notification> findByUserIdAndStatus(String userId, NotificationStatus status);

    List<Notification> findByEventInitiatedBetween(LocalDateTime start, LocalDateTime end);


    @Query("{'user_id': ?0, 'status': ?1}")
    List<Notification> findUserNotificationsByStatus(String userId, NotificationStatus status);

}