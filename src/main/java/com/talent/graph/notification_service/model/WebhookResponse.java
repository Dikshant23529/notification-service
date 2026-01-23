package com.talent.graph.notification_service.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class WebhookResponse {

    private boolean success;
    private String message;
    private String requestId;
    private Instant timeStamp;

    public static WebhookResponse success(String message){
        return WebhookResponse.builder()
                .success(true)
                .message(message)
                .timeStamp(Instant.now())
                .build();
    }

    public static WebhookResponse error(String message){
        return WebhookResponse.builder()
                .success(false)
                .message(message)
                .timeStamp(Instant.now())
                .build();
    }

}
