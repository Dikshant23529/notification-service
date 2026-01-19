package com.talent.graph.notification_service.repository;

import com.talent.graph.notification_service.model.NotificationTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationTemplateRepository extends MongoRepository<NotificationTemplate, String> {

    // Find template by name
    Optional<NotificationTemplate> findByName(String name);

    List<NotificationTemplate> findByNameContainingIgnoreCase(String keyword);

    List<NotificationTemplate> findByDescriptionContainingIgnoreCase(String keyword);

    @Query("{'$or': [{'name': {$regex: ?0, $options: 'i'}}, {'description': {$regex: ?0, $options: 'i'}}]}")
    List<NotificationTemplate> searchTemplates(String searchTerm);

    // Check if template name exists
    boolean existsByName(String name);

    List<NotificationTemplate> findAllByOrderByCreatedAtDesc();
}