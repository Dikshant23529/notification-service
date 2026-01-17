//package com.talent.graph.notification_service.dto;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from JSON
//public class NotificationResponseDTO {
//
//    private String id;
//
//    private String userId;
//
//    private String recipientEmail;
//
//    private String subject;
//
//    // Optional - include body only in detail view, not list view
//    private String body;
//
//    private String status;
//
//    private String priority;
//
//    private String referenceId;
//
//    private String resourceType;
//
//    private String eventType;
//
//    private String sourceService;
//
//    private Integer retryCount;
//
//    private String failureReason;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime scheduledFor;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime sentAt;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createdAt;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime updatedAt;
//
//    // Factory methods for different use cases:
//
//    // For list view (without body for performance)
//    public static NotificationResponseDTO forListView(String id, String recipientEmail,
//                                                     String subject, String status,
//                                                     String referenceId, String eventType,
//                                                     LocalDateTime createdAt, LocalDateTime sentAt) {
//        return NotificationResponseDTO.builder()
//                .id(id)
//                .recipientEmail(recipientEmail)
//                .subject(subject)
//                .status(status)
//                .referenceId(referenceId)
//                .eventType(eventType)
//                .createdAt(createdAt)
//                .sentAt(sentAt)
//                .build();
//    }
//
//    // For detail view (with all information)
//    public static NotificationResponseDTO forDetailView(String id, String userId,
//                                                       String recipientEmail, String subject,
//                                                       String body, String status, String priority,
//                                                       String referenceId, String resourceType,
//                                                       String eventType, String sourceService,
//                                                       Integer retryCount, String failureReason,
//                                                       LocalDateTime scheduledFor, LocalDateTime sentAt,
//                                                       LocalDateTime createdAt, LocalDateTime updatedAt) {
//        return NotificationResponseDTO.builder()
//                .id(id)
//                .userId(userId)
//                .recipientEmail(recipientEmail)
//                .subject(subject)
//                .body(body)
//                .status(status)
//                .priority(priority)
//                .referenceId(referenceId)
//                .resourceType(resourceType)
//                .eventType(eventType)
//                .sourceService(sourceService)
//                .retryCount(retryCount)
//                .failureReason(failureReason)
//                .scheduledFor(scheduledFor)
//                .sentAt(sentAt)
//                .createdAt(createdAt)
//                .updatedAt(updatedAt)
//                .build();
//    }
//
//    // For status response
//    public static NotificationResponseDTO forStatus(String id, String recipientEmail,
//                                                   String status, LocalDateTime sentAt,
//                                                   Integer retryCount, String failureReason) {
//        return NotificationResponseDTO.builder()
//                .id(id)
//                .recipientEmail(recipientEmail)
//                .status(status)
//                .sentAt(sentAt)
//                .retryCount(retryCount)
//                .failureReason(failureReason)
//                .build();
//    }
//}