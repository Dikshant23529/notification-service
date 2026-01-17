//package com.talent.graph.notification_service.service.impl;
//
//import com.talent.graph.notification_service.model.NotificationPreference;
//import com.talent.graph.notification_service.repository.NotificationPreferenceRepository;
//import com.talent.graph.notification_service.service.NotificationPreferenceService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.util.Map;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class NotificationPreferenceServiceImpl implements NotificationPreferenceService {
//
//    private final NotificationPreferenceRepository preferenceRepository;
//
//    @Override
//    public NotificationPreference getUserPreferences(String userId) {
//        log.info("Fetching preferences for user: {}", userId);
//
//        return preferenceRepository.findByUserId(userId)
//                .orElseGet(() -> createDefaultPreferences(userId));
//    }
//
//    @Override
//    public NotificationPreference updateUserPreferences(String userId, Map<String, Object> preferences) {
//        log.info("Updating preferences for user: {}", userId);
//
//        NotificationPreference preference = preferenceRepository.findByUserId(userId)
//                .orElseGet(() -> {
//                    NotificationPreference newPref = createDefaultPreferences(userId);
//                    return preferenceRepository.save(newPref);
//                });
//
//        // Update email preferences
//        if (preferences.containsKey("emailEnabled")) {
//            preference.setEmailEnabled(Boolean.parseBoolean(preferences.get("emailEnabled").toString()));
//        }
//
//        if (preferences.containsKey("smsEnabled")) {
//            preference.setSmsEnabled(Boolean.parseBoolean(preferences.get("smsEnabled").toString()));
//        }
//
//        if (preferences.containsKey("pushEnabled")) {
//            preference.setPushEnabled(Boolean.parseBoolean(preferences.get("pushEnabled").toString()));
//        }
//
//        if (preferences.containsKey("internalEnabled")) {
//            preference.setInternalEnabled(Boolean.parseBoolean(preferences.get("internalEnabled").toString()));
//        }
//
//        // Update quiet hours
//        if (preferences.containsKey("quietHoursStart")) {
//            preference.setQuietHoursStart(preferences.get("quietHoursStart").toString());
//        }
//
//        if (preferences.containsKey("quietHoursEnd")) {
//            preference.setQuietHoursEnd(preferences.get("quietHoursEnd").toString());
//        }
//
//        if (preferences.containsKey("timezone")) {
//            preference.setTimezone(preferences.get("timezone").toString());
//        }
//
//        // Update category preferences if provided
//        // This would require parsing the category preferences map
//
//        preference.setUpdatedAt(LocalDateTime.now());
//
//        NotificationPreference updated = preferenceRepository.save(preference);
//        log.info("Preferences updated for user: {}", userId);
//
//        return updated;
//    }
//
//    @Override
//    public boolean isNotificationAllowed(String userId, String notificationType, String category) {
//        NotificationPreference preference = getUserPreferences(userId);
//
//        // First check if notification type is enabled globally
//        boolean typeEnabled = switch (notificationType.toUpperCase()) {
//            case "EMAIL" -> preference.isEmailEnabled();
//            case "SMS" -> preference.isSmsEnabled();
//            case "PUSH" -> preference.isPushEnabled();
//            case "INTERNAL" -> preference.isInternalEnabled();
//            default -> true; // Default to true for unknown types
//        };
//
//        if (!typeEnabled) {
//            log.debug("Notification type {} is disabled for user {}", notificationType, userId);
//            return false;
//        }
//
//        // Check quiet hours
//        if (isInQuietHours(userId)) {
//            log.debug("User {} is in quiet hours", userId);
//            return false;
//        }
//
//        // TODO: Check category-specific preferences if implemented
//        // This would involve checking the categoryPreferences map
//
//        return true;
//    }
//
//    @Override
//    public void setQuietHours(String userId, String startTime, String endTime, String timezone) {
//        log.info("Setting quiet hours for user: {} - {} to {} ({})",
//                userId, startTime, endTime, timezone);
//
//        NotificationPreference preference = preferenceRepository.findByUserId(userId)
//                .orElseGet(() -> createDefaultPreferences(userId));
//
//        // Validate time format (HH:mm)
//        if (!isValidTimeFormat(startTime) || !isValidTimeFormat(endTime)) {
//            throw new IllegalArgumentException("Time must be in HH:mm format");
//        }
//
//        preference.setQuietHoursStart(startTime);
//        preference.setQuietHoursEnd(endTime);
//        preference.setTimezone(timezone != null ? timezone : "UTC");
//        preference.setUpdatedAt(LocalDateTime.now());
//
//        preferenceRepository.save(preference);
//    }
//
//    @Override
//    public void disableAllNotifications(String userId) {
//        log.info("Disabling all notifications for user: {}", userId);
//
//        NotificationPreference preference = preferenceRepository.findByUserId(userId)
//                .orElseGet(() -> createDefaultPreferences(userId));
//
//        preference.setEmailEnabled(false);
//        preference.setSmsEnabled(false);
//        preference.setPushEnabled(false);
//        preference.setInternalEnabled(false);
//        preference.setUpdatedAt(LocalDateTime.now());
//
//        preferenceRepository.save(preference);
//    }
//
//    @Override
//    public void enableAllNotifications(String userId) {
//        log.info("Enabling all notifications for user: {}", userId);
//
//        NotificationPreference preference = preferenceRepository.findByUserId(userId)
//                .orElseGet(() -> createDefaultPreferences(userId));
//
//        preference.setEmailEnabled(true);
//        preference.setSmsEnabled(true);
//        preference.setPushEnabled(true);
//        preference.setInternalEnabled(true);
//        preference.setUpdatedAt(LocalDateTime.now());
//
//        preferenceRepository.save(preference);
//    }
//
//    @Override
//    public boolean isInQuietHours(String userId) {
//        NotificationPreference preference = preferenceRepository.findByUserId(userId)
//                .orElse(null);
//
//        if (preference == null ||
//            preference.getQuietHoursStart() == null ||
//            preference.getQuietHoursEnd() == null) {
//            return false; // No quiet hours configured
//        }
//
//        try {
//            String timezone = preference.getTimezone() != null ? preference.getTimezone() : "UTC";
//            ZoneId zoneId = ZoneId.of(timezone);
//            ZonedDateTime now = ZonedDateTime.now(zoneId);
//
//            LocalTime startTime = LocalTime.parse(preference.getQuietHoursStart());
//            LocalTime endTime = LocalTime.parse(preference.getQuietHoursEnd());
//            LocalTime currentTime = now.toLocalTime();
//
//            // Handle cases where quiet hours span midnight
//            if (startTime.isBefore(endTime)) {
//                // Normal case: 22:00 to 08:00
//                return currentTime.isAfter(startTime) && currentTime.isBefore(endTime);
//            } else {
//                // Spanning midnight: 22:00 to 06:00
//                return currentTime.isAfter(startTime) || currentTime.isBefore(endTime);
//            }
//
//        } catch (Exception e) {
//            log.error("Error checking quiet hours for user {}: {}", userId, e.getMessage());
//            return false;
//        }
//    }
//
//    // Helper method to create default preferences for a new user
//    private NotificationPreference createDefaultPreferences(String userId) {
//        log.info("Creating default preferences for new user: {}", userId);
//
//        NotificationPreference preference = new NotificationPreference();
//        preference.setId(UUID.randomUUID());
//        preference.setUserId(userId);
//        preference.setEmailEnabled(true);
//        preference.setSmsEnabled(false);
//        preference.setPushEnabled(false);
//        preference.setInternalEnabled(true);
//
//        // Set default quiet hours (10 PM to 8 AM)
//        preference.setQuietHoursStart("22:00");
//        preference.setQuietHoursEnd("08:00");
//        preference.setTimezone("UTC");
//
//        LocalDateTime now = LocalDateTime.now();
//        preference.setCreatedAt(now);
//        preference.setUpdatedAt(now);
//
//        return preferenceRepository.save(preference);
//    }
//
//    // Helper method to validate time format
//    private boolean isValidTimeFormat(String time) {
//        if (time == null || time.isBlank()) {
//            return false;
//        }
//        try {
//            LocalTime.parse(time);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//}