//package com.talent.graph.notification_service.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//import java.util.Map;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class NotificationEvent {
//    private String eventId;
//    private String eventType; // "resource.created", "task.completed", "user.registered"
//    private String sourceService; // "resource-service", "user-service"
//    private LocalDateTime eventTimestamp;
//
//    // Payload with user and resource info
//    private Map<String, Object> data;
//    /*
//    Example data:
//    {
//        "userId": "user123",
//        "userEmail": "user@example.com",
//        "userName": "John Doe",
//        "resourceId": "resource456",
//        "resourceName": "MyVM",
//        "resourceType": "VirtualMachine",
//        "status": "SUCCESS",
//        "additionalData": {...}
//    }
//    */
//
//    // Metadata
//    private String correlationId;
//    private Integer version;
//    private boolean isTestEvent;
//}