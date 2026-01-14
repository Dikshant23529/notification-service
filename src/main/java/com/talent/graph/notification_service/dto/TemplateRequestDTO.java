package com.talent.graph.notification_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateRequestDTO {
    
    @NotBlank(message = "Template name is required")
    @Size(min = 3, max = 100, message = "Template name must be between 3 and 100 characters")
    private String name;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
    
    @NotBlank(message = "Subject template is required")
    @Size(max = 200, message = "Subject template cannot exceed 200 characters")
    private String subjectTemplate;
    
    @NotBlank(message = "Body template is required")
    @Size(max = 5000, message = "Body template cannot exceed 5000 characters")
    private String bodyTemplate;
    
    private String category; // Examples: "RESOURCE", "ALERT", "USER", "SYSTEM"
    
    @Size(min = 2, max = 5, message = "Language code must be 2-5 characters")
    private String language = "en";
    
    // Default variables with descriptions
    // Example: {{"userName": "Full name of the user"}}
    private Map<String, String> defaultVariables;
    
    // You can add validation methods if needed
    public void validateTemplateVariables() {
        // Check if required variables are present in templates
        if (subjectTemplate != null && bodyTemplate != null) {
            // You could add validation logic here
            // For example, ensure all defaultVariables keys are used in templates
        }
    }
    
    // Builder pattern with defaults
    public static TemplateRequestDTOBuilder builder() {
        return new TemplateRequestDTOBuilder()
                .language("en");
    }
}