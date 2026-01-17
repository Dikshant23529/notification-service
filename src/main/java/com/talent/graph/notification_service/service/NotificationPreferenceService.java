//package com.talent.graph.notification_service.service;
//
//import com.talent.graph.notification_service.model.NotificationPreference;
//
//import java.util.Map;
//
//public interface NotificationPreferenceService {
//
//    // Get preferences for user
//    NotificationPreference getUserPreferences(String userId);
//
//    // Update user preferences
//    NotificationPreference updateUserPreferences(String userId, Map<String, Object> preferences);
//
//    // Check if user allows notification type
//    boolean isNotificationAllowed(String userId, String notificationType, String category);
//
//    // Set quiet hours for user
//    void setQuietHours(String userId, String startTime, String endTime, String timezone);
//
//    // Disable all notifications for user
//    void disableAllNotifications(String userId);
//
//    // Enable all notifications for user
//    void enableAllNotifications(String userId);
//
//    // Check if current time is in quiet hours for user
//    boolean isInQuietHours(String userId);
//}