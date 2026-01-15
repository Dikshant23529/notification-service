//package com.talent.graph.notification_service.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.time.LocalDateTime;
//import java.util.Map;
//import java.util.UUID;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//public class NotificationPreference {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID id;
//
//    @Column(nullable = false, unique = true)
//    private String userId;
//
//    @Column(nullable = false)
//    private boolean emailEnabled = true;
//
//    @Column(nullable = false)
//    private boolean smsEnabled = false;
//
//    @Column(nullable = false)
//    private boolean pushEnabled = false;
//
//    @Column(nullable = false)
//    private boolean internalEnabled = true;
//
//    @ElementCollection
//    @CollectionTable(name = "category_preferences",
//            joinColumns = @JoinColumn(name = "preference_id"))
//    @MapKeyColumn(name = "category")
//    @Column(name = "channel_preferences")
//    private Map<String, String> categoryPreferences;
//
//    private String quietHoursStart;
//    private String quietHoursEnd;
//    private String timezone;
//
//    @CreationTimestamp
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    private LocalDateTime updatedAt;
//}