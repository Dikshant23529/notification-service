package com.talent.graph.notification_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "notification_template")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String subjectTemplate;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String bodyTemplate;

    private String category;
    private String language = "en";

    @ElementCollection
    @CollectionTable(name = "template_variables",
            joinColumns = @JoinColumn(name = "template_id"))
    @MapKeyColumn(name = "variable_name")
    @Column(name = "variable_description")
    private Map<String, String> variables;

    @Column(nullable = false)
    private boolean isActive = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}