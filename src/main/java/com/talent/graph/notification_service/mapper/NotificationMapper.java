package com.talent.graph.notification_service.mapper;

import com.talent.graph.notification_service.dto.EventNotificationRequestDTO;
import com.talent.graph.notification_service.dto.NotificationRequestDTO;
import com.talent.graph.notification_service.dto.NotificationResponseDTO;
import com.talent.graph.notification_service.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    
//    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "status", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "sentAt", ignore = true)
//    @Mapping(target = "retryCount", ignore = true)
//    @Mapping(target = "failureReason", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    Notification toEntity(NotificationRequestDTO requestDTO);
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "subject", expression = "java(generateSubjectFromEvent(eventDTO))")
//    @Mapping(target = "body", expression = "java(generateBodyFromEvent(eventDTO))")
//    @Mapping(target = "status", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "sentAt", ignore = true)
//    @Mapping(target = "retryCount", ignore = true)
//    @Mapping(target = "failureReason", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    Notification toEntity(EventNotificationRequestDTO eventDTO);
//
//    NotificationResponseDTO toResponseDTO(Notification notification);
//
//    default String generateSubjectFromEvent(EventNotificationRequestDTO eventDTO) {
//        switch (eventDTO.getEventType()) {
//            case "RESOURCE_CREATED":
//                return "Resource Creation Started";
//            case "RESOURCE_CREATED_COMPLETED":
//                return "Resource Created Successfully";
//            case "RESOURCE_CREATION_FAILED":
//                return "Resource Creation Failed";
//            default:
//                return "Notification from System";
//        }
//    }
//
//    default String generateBodyFromEvent(EventNotificationRequestDTO eventDTO) {
//        return String.format(
//            "Hello %s,\n\nYour resource '%s' (%s) event: %s\n\nResource ID: %s\n\nAdditional Data: %s",
//            eventDTO.getUserName() != null ? eventDTO.getUserName() : "User",
//            eventDTO.getResourceName() != null ? eventDTO.getResourceName() : "Resource",
//            eventDTO.getResourceType() != null ? eventDTO.getResourceType() : "Unknown",
//            eventDTO.getEventType(),
//            eventDTO.getResourceId(),
//            eventDTO.getAdditionalData() != null ? eventDTO.getAdditionalData().toString() : "None"
//        );
//    }


    Notification toEntity(NotificationRequestDTO requestDTO);

    Notification toEntity(EventNotificationRequestDTO eventDTO);

    NotificationResponseDTO toResponseDTO(Notification notification);

}