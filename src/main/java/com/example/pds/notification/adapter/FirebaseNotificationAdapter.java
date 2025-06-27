package com.example.pds.notification.adapter;

import com.example.pds.notification.NotificationEvent;
import com.example.pds.model.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class FirebaseNotificationAdapter implements NotificationAdapter {
    
    @Override
    public void sendNotification(NotificationEvent event, Usuario usuario) {
        // Adaptación específica para Firebase
        System.out.println("=== FIREBASE ADAPTER ===");
        System.out.println("Adaptando notificación para Firebase...");
        
        // Simulación de la adaptación de datos para Firebase
        String firebaseMessage = adaptToFirebaseFormat(event, usuario);
        
        System.out.println("Firebase Message: " + firebaseMessage);
        System.out.println("Enviando a Firebase...");
        System.out.println("========================");
        
        // En una implementación real:
        // firebaseService.send(firebaseMessage);
    }
    
    @Override
    public boolean supports(NotificationEvent.NotificationType type) {
        // Firebase soporta todos los tipos de notificación
        return true;
    }
    
    private String adaptToFirebaseFormat(NotificationEvent event, Usuario usuario) {
        return String.format(
            "Firebase Notification - User: %s, Type: %s, Message: %s, Partido: %d",
            usuario.getUsername(),
            event.getType(),
            event.getMessage(),
            event.getPartido().getId()
        );
    }
} 