package com.example.pds.notification.strategy;

import com.example.pds.notification.NotificationEvent;
import com.example.pds.model.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class PushNotificationStrategy implements NotificationStrategy {
    
    @Override
    public void sendNotification(NotificationEvent event, Usuario usuario) {
        // Simulación de notificación push con Firebase
        System.out.println("=== PUSH NOTIFICATION (Firebase) ===");
        System.out.println("User ID: " + usuario.getId());
        System.out.println("Username: " + usuario.getUsername());
        System.out.println("Title: Notificación de Partido");
        System.out.println("Body: " + event.getMessage());
        System.out.println("Data: {");
        System.out.println("  partidoId: " + event.getPartido().getId());
        System.out.println("  deporte: " + event.getPartido().getDeporte().getNombre());
        System.out.println("  tipo: " + event.getType());
        System.out.println("}");
        System.out.println("===================================");
        
        // En una implementación real, aquí se usaría Firebase Admin SDK
        // firebaseService.sendPushNotification(usuario.getFirebaseToken(), title, body, data);
    }
    
    @Override
    public String getStrategyName() {
        return "PUSH";
    }
} 