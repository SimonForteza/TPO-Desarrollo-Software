package com.example.pds.notification.strategy;

import com.example.pds.notification.NotificationEvent;
import com.example.pds.model.entity.Usuario;

public interface NotificationStrategy {
    void sendNotification(NotificationEvent event, Usuario usuario);
    String getStrategyName();
} 