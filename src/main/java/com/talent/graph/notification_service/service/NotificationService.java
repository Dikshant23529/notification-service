package com.talent.graph.notification_service.service;

import com.talent.graph.notification_service.model.Notification;

public interface NotificationService {

    public Notification initiateNotification(String userId, String recipientEmail, String templateName);

    public void processPendingNotifications();

    public void sendNotification(Notification notification);

    public void createNotificationLog(Notification notification, String responseMessage, boolean success);

}   