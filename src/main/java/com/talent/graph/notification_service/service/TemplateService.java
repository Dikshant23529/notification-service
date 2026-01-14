package com.talent.graph.notification_service.service;

import com.talent.graph.notification_service.dto.TemplateRequestDTO;
import com.talent.graph.notification_service.dto.TemplateResponseDTO;
import com.talent.graph.notification_service.model.NotificationTemplate;

import java.util.List;
import java.util.Map;

public interface TemplateService {
    
    // Create new template
    TemplateResponseDTO createTemplate(TemplateRequestDTO requestDTO);
    
    // Update existing template
    TemplateResponseDTO updateTemplate(String templateId, TemplateRequestDTO requestDTO);
    
    // Get template by ID
    TemplateResponseDTO getTemplateById(String id);
    
    // Get template by name
    TemplateResponseDTO getTemplateByName(String name);
    
    // Get all active templates
    List<TemplateResponseDTO> getAllActiveTemplates();
    
    // Get template for event type
    NotificationTemplate getTemplateForEvent(String eventType, String language);
    
    // Process template with variables
    Map<String, String> processTemplate(String templateId, Map<String, Object> variables);
    
    // Deactivate template
    void deactivateTemplate(String templateId);
    
    // Delete template
    void deleteTemplate(String templateId);
    
    // Check if template exists
    boolean templateExists(String name);
}