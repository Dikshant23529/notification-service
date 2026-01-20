package com.talent.graph.notification_service.controller;


import com.talent.graph.notification_service.service.impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    private NotificationServiceImpl notificationService;


    @PostMapping("/send")
    public boolean sendEmail() {
        try {
            notificationService.sendNotification();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
