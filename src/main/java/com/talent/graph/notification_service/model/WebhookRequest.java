package com.talent.graph.notification_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document
@Data
public class WebhookRequest {

    @Id
    private String eventId;

    private String eventType = "login_event";

    private String timeStamp;

    private String service;

    private Map<String, Object> payload;

    private String version;

    private String signature;

}
