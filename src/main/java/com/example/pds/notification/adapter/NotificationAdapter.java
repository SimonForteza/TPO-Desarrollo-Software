package com.example.pds.notification.adapter;

import com.example.pds.notification.NotificationEvent;
import com.example.pds.model.entity.Usuario;

public interface NotificationAdapter {
    void sendNotification(NotificationEvent event, Usuario usuario);
    boolean supports(NotificationEvent.NotificationType type);
} 