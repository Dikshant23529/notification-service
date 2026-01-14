package com.talent.graph.notification_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PreferenceUpdateRequestDTO {
    
    private Boolean emailEnabled;
    private Boolean smsEnabled;
    private Boolean pushEnabled;
    private Boolean internalEnabled;
    
    private String quietHoursStart;  // Format: "HH:mm"
    private String quietHoursEnd;    // Format: "HH:mm"
    private String timezone;         // Example: "America/New_York", "UTC"
    
    // For category-specific preferences
    // Key: category name, Value: comma-separated enabled channels
    // Example: {"RESOURCE": "email,internal", "ALERT": "email,sms"}
    private Map<String, String> categoryPreferences;
    
    // Validation method
    public void validate() {
        if (quietHoursStart != null || quietHoursEnd != null) {
            if (quietHoursStart == null || quietHoursEnd == null) {
                throw new IllegalArgumentException("Both quietHoursStart and quietHoursEnd must be provided");
            }
        }
    }
}