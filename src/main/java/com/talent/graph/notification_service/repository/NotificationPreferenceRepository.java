//package com.talent.graph.notification_service.repository;
//
//import com.talent.graph.notification_service.model.NotificationPreference;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface NotificationPreferenceRepository extends JpaRepository<NotificationPreference, String> {
//
//    // Find preference by user ID
//    Optional<NotificationPreference> findByUserId(String userId);
//
//    // Check if preference exists for user
//    boolean existsByUserId(String userId);
//}