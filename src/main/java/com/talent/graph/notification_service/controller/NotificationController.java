package com.talent.graph.notification_service.controller;


import com.talent.graph.notification_service.model.Notification;
import com.talent.graph.notification_service.service.impl.NotificationServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class NotificationController {

    @Autowired
    private NotificationServiceImpl notificationService;
    @Autowired
    private HttpServletResponse httpServletResponse;

    @PostMapping("/create")
    public ResponseEntity<String> createNotificationRecord(){

        notificationService.initiateNotification(UUID.randomUUID().toString(), "latherdikshant0@gmail.com", "1234");

        return ResponseEntity.ok("api called");

    }


    @PostMapping("/send")
    public boolean sendEmail() {
        try {
            notificationService.processPendingNotifications();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
