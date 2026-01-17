//package com.talent.graph.notification_service.service.impl;
//
//import com.talent.graph.notification_service.dto.TemplateRequestDTO;
//import com.talent.graph.notification_service.dto.TemplateResponseDTO;
//import com.talent.graph.notification_service.mapper.TemplateMapper;
//import com.talent.graph.notification_service.model.NotificationTemplate;
//import com.talent.graph.notification_service.repository.NotificationTemplateRepository;
//import com.talent.graph.notification_service.service.TemplateService;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class TemplateServiceImpl implements TemplateService {
//
//    private final NotificationTemplateRepository templateRepository;
//    private final TemplateMapper templateMapper;
//
//    @Override
//    @Transactional
//    public TemplateResponseDTO createTemplate(TemplateRequestDTO requestDTO) {
//        log.info("Creating template: {}", requestDTO.getName());
//
//        // Check if template with same name exists
//        if (templateRepository.existsByName(requestDTO.getName())) {
//            throw new RuntimeException("Template with name '" + requestDTO.getName() + "' already exists");
//        }
//
//        NotificationTemplate template = templateMapper.toEntity(requestDTO);
//        template.setId(UUID.randomUUID());
//        template.setActive(true);
//
//        NotificationTemplate savedTemplate = templateRepository.save(template);
//        log.info("Template created with ID: {}", savedTemplate.getId());
//
//        return templateMapper.toResponseDTO(savedTemplate);
//    }
//
//    @Override
//    @Transactional
//    public TemplateResponseDTO updateTemplate(String templateId, TemplateRequestDTO requestDTO) {
//        log.info("Updating template: {}", templateId);
//
//        NotificationTemplate template = templateRepository.findById(templateId)
//                .orElseThrow(() -> new RuntimeException("Template not found: " + templateId));
//
//        // Update fields
//        template.setName(requestDTO.getName());
//        template.setDescription(requestDTO.getDescription());
//        template.setSubjectTemplate(requestDTO.getSubjectTemplate());
//        template.setBodyTemplate(requestDTO.getBodyTemplate());
//        template.setCategory(requestDTO.getCategory());
//        template.setLanguage(requestDTO.getLanguage());
//
//        if (requestDTO.getDefaultVariables() != null) {
//            template.setVariables(requestDTO.getDefaultVariables());
//        }
//
//        NotificationTemplate updatedTemplate = templateRepository.save(template);
//        log.info("Template updated: {}", templateId);
//
//        return templateMapper.toResponseDTO(updatedTemplate);
//    }
//
//    @Override
//    public TemplateResponseDTO getTemplateById(String id) {
//        NotificationTemplate template = templateRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Template not found: " + id));
//        return templateMapper.toResponseDTO(template);
//    }
//
//    @Override
//    public TemplateResponseDTO getTemplateByName(String name) {
//        NotificationTemplate template = templateRepository.findByName(name)
//                .orElseThrow(() -> new RuntimeException("Template not found with name: " + name));
//        return templateMapper.toResponseDTO(template);
//    }
//
//    @Override
//    public List<TemplateResponseDTO> getAllActiveTemplates() {
//        List<NotificationTemplate> templates = templateRepository.findByIsActiveTrue();
//        return templates.stream()
//                .map(templateMapper::toResponseDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public NotificationTemplate getTemplateForEvent(String eventType, String language) {
//        return templateRepository.findByEventTypeAndLanguage(eventType, language)
//                .orElseThrow(() -> new RuntimeException(
//                        "No template found for event: " + eventType + " and language: " + language));
//    }
//
//    @Override
//    public Map<String, String> processTemplate(String templateId, Map<String, Object> variables) {
//        NotificationTemplate template = templateRepository.findById(templateId)
//                .orElseThrow(() -> new RuntimeException("Template not found: " + templateId));
//
//        // Process subject and body templates
//        String processedSubject = processTemplateString(template.getSubjectTemplate(), variables);
//        String processedBody = processTemplateString(template.getBodyTemplate(), variables);
//
//        return Map.of(
//                "subject", processedSubject,
//                "body", processedBody
//        );
//    }
//
//    @Override
//    @Transactional
//    public void deactivateTemplate(String templateId) {
//        NotificationTemplate template = templateRepository.findById(templateId)
//                .orElseThrow(() -> new RuntimeException("Template not found: " + templateId));
//
//        template.setActive(false);
//        templateRepository.save(template);
//        log.info("Template deactivated: {}", templateId);
//    }
//
//    @Override
//    @Transactional
//    public void deleteTemplate(String templateId) {
//        if (!templateRepository.existsById(templateId)) {
//            throw new RuntimeException("Template not found: " + templateId);
//        }
//
//        templateRepository.deleteById(templateId);
//        log.info("Template deleted: {}", templateId);
//    }
//
//    @Override
//    public boolean templateExists(String name) {
//        return templateRepository.existsByName(name);
//    }
//
//    private String processTemplateString(String template, Map<String, Object> variables) {
//        String processed = template;
//
//        if (variables != null) {
//            for (Map.Entry<String, Object> entry : variables.entrySet()) {
//                String placeholder = "{{" + entry.getKey() + "}}";
//                String value = entry.getValue() != null ? entry.getValue().toString() : "";
//                processed = processed.replace(placeholder, value);
//            }
//        }
//
//        return processed;
//    }
//}