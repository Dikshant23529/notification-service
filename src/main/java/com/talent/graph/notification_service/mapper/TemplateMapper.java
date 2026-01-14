package com.talent.graph.notification_service.mapper;

import com.talent.graph.notification_service.dto.TemplateRequestDTO;
import com.talent.graph.notification_service.dto.TemplateResponseDTO;
import com.talent.graph.notification_service.model.NotificationTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TemplateMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    NotificationTemplate toEntity(TemplateRequestDTO requestDTO);
    
    TemplateResponseDTO toResponseDTO(NotificationTemplate template);
}