//package com.talent.graph.notification_service.dto;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class NotificationRequestDTO {
//
//    private String UserId;
//
//    @NotBlank(message = "Recipient email is required")
//    @Email(message = "Invalid email format")
//    private String recipientEmail;
//
//    @NotBlank(message = "Subject is required")
//    @Size(max = 200, message = "Subject cannot exceed 200 characters")
//    private String subject;
//
//    @NotBlank(message = "Body is required")
//    private String body;
//
//    // Optional fields
//    private String referenceId;
//    private String resourceType;
//    private String eventType;
//    private String priority = "NORMAL"; // LOW, NORMAL, HIGH
//
//    // Getters and setters
//}