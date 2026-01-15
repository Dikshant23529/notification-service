//package com.talent.graph.notification_service.dto;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//import java.util.Map;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class TemplateResponseDTO {
//
//    private String id;
//
//    private String name;
//
//    private String description;
//
//    private String category;
//
//    private String language;
//
//    private boolean active;
//
//    // Template content (optional - you might want to exclude in list view)
//    private String subjectTemplate;
//
//    private String bodyTemplate;
//
//    private Map<String, String> variables;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createdAt;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime updatedAt;
//
//    // You can create different versions for different use cases:
//
//    // For list view (without template content)
//    public static TemplateResponseDTO forListView(String id, String name, String description,
//                                                  String category, String language,
//                                                  boolean active, LocalDateTime createdAt) {
//        return TemplateResponseDTO.builder()
//                .id(id)
//                .name(name)
//                .description(description)
//                .category(category)
//                .language(language)
//                .active(active)
//                .createdAt(createdAt)
//                .build();
//    }
//
//    // For detail view (with template content)
//    public static TemplateResponseDTO forDetailView(String id, String name, String description,
//                                                    String category, String language,
//                                                    boolean active, String subjectTemplate,
//                                                    String bodyTemplate, Map<String, String> variables,
//                                                    LocalDateTime createdAt, LocalDateTime updatedAt) {
//        return TemplateResponseDTO.builder()
//                .id(id)
//                .name(name)
//                .description(description)
//                .category(category)
//                .language(language)
//                .active(active)
//                .subjectTemplate(subjectTemplate)
//                .bodyTemplate(bodyTemplate)
//                .variables(variables)
//                .createdAt(createdAt)
//                .updatedAt(updatedAt)
//                .build();
//    }
//}