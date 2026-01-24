package com.talent.graph.notification_service.controller;

import com.talent.graph.notification_service.model.WebhookRequest;
import com.talent.graph.notification_service.service.impl.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/webhooks")
@RequiredArgsConstructor
public class WebhookController {

    @Autowired
    private NotificationServiceImpl notificationService;

//    private static final Logger log = LoggerFactory.getLogger(WebhookController.class);

    private List<WebhookRequest> recivedPayloadRequest = Collections.synchronizedList(new ArrayList<>());

    public ResponseEntity<Map<String, String>> recivedWebhook(@RequestBody WebhookRequest payload,
                                                              @RequestHeader(value = "X-Signature", required = false) String signature
    ) {
        if (payload.getEventType() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "event type is required"));
        }

        recivedPayloadRequest.add(payload);
        processInBackground(payload);

        return ResponseEntity.accepted().body(Map.of(
                "status", "accepted",
                "message", "Webhook received and will be processed",
                "timestamp", String.valueOf(System.currentTimeMillis())

        ));
    }

    @GetMapping("/webhook")
    public List<WebhookRequest> getAllWebhooks() {
        return new ArrayList<>(recivedPayloadRequest);
    }


    public void processInBackground(WebhookRequest webhookRequest) {

        try {
            Thread.sleep(2000);
            switch (webhookRequest.getEventType()) {
                case "login_event":
                    System.out.println(webhookRequest.getEventType());
                    notificationService.processPendingNotifications();
                    break;
                default:
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

}
