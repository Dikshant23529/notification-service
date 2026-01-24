package com.talent.graph.notification_service.controller;


import com.talent.graph.notification_service.service.impl.NotificationServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notification")
@Deprecated
public class NotificationController {

    @Autowired
    private NotificationServiceImpl notificationService;
    @Autowired
    private HttpServletResponse httpServletResponse;

    @PostMapping()
    public ResponseEntity<String> createNotificationRecord(){

        notificationService.initiateNotification(UUID.randomUUID().toString(), "latherdikshant0@gmail.com", "1234");

        return ResponseEntity.ok("api called");

    }

    @PostMapping()
    public boolean sendEmail() {
        try {
            notificationService.processPendingNotifications();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
