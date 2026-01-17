//package com.talent.graph.notification_service.repository;
//
//import com.talent.graph.notification_service.model.NotificationTemplate;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, String> {
//
//    // Find template by name
//    Optional<NotificationTemplate> findByName(String name);
//
//    // Find active templates
//    List<NotificationTemplate> findByIsActiveTrue();
//
//    // Find templates by category
//    List<NotificationTemplate> findByCategory(String category);
//
//    // Find templates by category and language
//    Optional<NotificationTemplate> findByCategoryAndLanguage(String category, String language);
//
//    // Find templates by event type
//    Optional<NotificationTemplate> findByEventTypeAndLanguage(String eventType, String language);
//
//    // Check if template name exists
//    boolean existsByName(String name);
//}