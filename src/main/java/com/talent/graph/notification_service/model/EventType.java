package com.talent.graph.notification_service.model;

public enum EventType {
    RESOURCE_CREATED,
    RESOURCE_UPDATED,
    RESOURCE_DELETED,
    TASK_STARTED,
    TASK_COMPLETED,
    TASK_FAILED,
    SYSTEM_ALERT,
    USER_INVITED,
    APPROVAL_REQUIRED
}