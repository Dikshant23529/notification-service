package com.talent.graph.notification_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.Map;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class WebhookRequest {

    private String eventId;

    private String eventType = "login_event";

    private String email;

    private String timeStamp;

    private String service;

    private Map<String, Object> payload;

    private String version;

    private String signature;

}
