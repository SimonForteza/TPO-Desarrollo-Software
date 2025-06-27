package com.example.pds.notification.strategy;

import com.example.pds.notification.NotificationEvent;
import com.example.pds.model.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationStrategy implements NotificationStrategy {
    
    @Override
    public void sendNotification(NotificationEvent event, Usuario usuario) {
        // Simulación de envío de email
        System.out.println("=== EMAIL NOTIFICATION ===");
        System.out.println("To: " + usuario.getEmail());
        System.out.println("Subject: Notificación de Partido - " + event.getType());
        System.out.println("Message: " + event.getMessage());
        System.out.println("Partido ID: " + event.getPartido().getId());
        System.out.println("Deporte: " + event.getPartido().getDeporte().getNombre());
        System.out.println("=========================");
        
        // En una implementación real, aquí se usaría un servicio de email como JavaMailSender
        // emailService.sendEmail(usuario.getEmail(), subject, message);
    }
    
    @Override
    public String getStrategyName() {
        return "EMAIL";
    }
} 