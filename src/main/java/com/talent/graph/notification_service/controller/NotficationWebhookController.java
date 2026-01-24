package com.talent.graph.notification_service.controller;

import com.talent.graph.notification_service.model.WebhookRequest;
import com.talent.graph.notification_service.model.WebhookResponse;
import com.talent.graph.notification_service.service.impl.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/webhooks")
@RequiredArgsConstructor
public class NotficationWebhookController {

    @Autowired
    private NotificationServiceImpl notificationService;

    private List<WebhookRequest> receivedPayloads = Collections.synchronizedList(new ArrayList<>());

    /**
     * FIX 1: Add @PostMapping annotation
     * This makes the endpoint accessible at POST /api/v1/webhooks
     */
    @PostMapping
    public ResponseEntity<WebhookResponse> receiveWebhook(
            @RequestBody WebhookRequest payload,
            @RequestHeader(value = "X-Signature", required = false) String signature) {

        log.info("📨 Webhook received: {}", payload.getEventType());

        // 1. Validate
        if (payload.getEventType() == null) {
            return ResponseEntity.badRequest()
                    .body(WebhookResponse.error("event type is required"));
        }

        // 2. Store for debugging
        receivedPayloads.add(payload);

        // 3. Process in background
        processInBackground(payload);

        // 4. Return immediate response
        return ResponseEntity.accepted()
                .body(WebhookResponse.success(
                        "Webhook received and will be processed"
                ));
    }

    /**
     * FIX 2: Add @GetMapping to make it accessible
     * View all received webhooks
     */
    @GetMapping
    public List<WebhookRequest> getAllWebhooks() {
        return new ArrayList<>(receivedPayloads);
    }

    /**
     * FIX 3: Add @Async for background processing
     * This prevents blocking the webhook response
     */
    @Async
    public void processInBackground(WebhookRequest webhookRequest) {
        log.info("🔄 Processing webhook in background: {}", webhookRequest.getEventType());

        try {
            // Add a small delay to simulate processing
            Thread.sleep(2000);

            // Handle different event types
            switch (webhookRequest.getEventType()) {
                case "login_event":
                    log.info("👤 Processing login event");

                    // Extract data from webhook payload
                    Map<String, Object> payload = webhookRequest.getPayload();
                    String userId = (String) payload.get("user_id");
                    String email = (String) payload.get("email");
                    String message = (String) payload.get("message");

                    // If userId/email not in payload, generate defaults
                    if (userId == null) {
                        userId = "user_" + System.currentTimeMillis();
                    }
                    if (email == null) {
                        email = "user@example.com";
                    }
                    if (message == null) {
                        message = "New login detected";
                    }

                    // USE YOUR EXISTING LOGIC!
                    // This creates a notification record in DB
                    notificationService.initiateNotification(userId, email, message);

                    // YOUR BACKGROUND JOB will pick it up later
                     notificationService.processPendingNotifications();
                    // Note: Don't call this here - it should run on a schedule

                    break;

                case "user_created":
                    log.info("👤 Processing user created event");
                    // Add similar logic for other events
                    break;

                case "payment_success":
                    log.info("💰 Processing payment success event");
                    // Add similar logic for other events
                    break;

                default:
                    log.warn("⚠️ Unknown event type: {}", webhookRequest.getEventType());
            }

            log.info("✅ Finished processing webhook: {}", webhookRequest.getEventType());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("❌ Webhook processing interrupted", e);
        } catch (Exception e) {
            log.error("❌ Error processing webhook", e);
        }
    }
}