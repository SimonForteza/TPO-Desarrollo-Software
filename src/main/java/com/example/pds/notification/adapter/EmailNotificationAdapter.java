package com.example.pds.notification.adapter;

import com.example.pds.notification.NotificationEvent;
import com.example.pds.model.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationAdapter implements NotificationAdapter {
    
    @Override
    public void sendNotification(NotificationEvent event, Usuario usuario) {
        // Adaptación específica para Email
        System.out.println("=== EMAIL ADAPTER ===");
        System.out.println("Adaptando notificación para Email...");
        
        // Simulación de la adaptación de datos para Email
        String emailSubject = adaptToEmailSubject(event);
        String emailBody = adaptToEmailBody(event, usuario);
        
        System.out.println("Email Subject: " + emailSubject);
        System.out.println("Email Body: " + emailBody);
        System.out.println("Enviando email...");
        System.out.println("===================");
        
        // En una implementación real:
        // emailService.sendEmail(usuario.getEmail(), emailSubject, emailBody);
    }
    
    @Override
    public boolean supports(NotificationEvent.NotificationType type) {
        // Email soporta todos los tipos de notificación
        return true;
    }
    
    private String adaptToEmailSubject(NotificationEvent event) {
        return String.format("Notificación de Partido - %s", event.getType());
    }
    
    private String adaptToEmailBody(NotificationEvent event, Usuario usuario) {
        return String.format(
            "Hola %s,\n\n%s\n\nDetalles del partido:\n- Deporte: %s\n- Fecha: %s\n- Ubicación: %s\n\nSaludos,\nEquipo de Gestión Deportiva",
            usuario.getUsername(),
            event.getMessage(),
            event.getPartido().getDeporte().getNombre(),
            event.getPartido().getFecha(),
            event.getPartido().getUbicacion().getNombreCalle() + " " + event.getPartido().getUbicacion().getNumero()
        );
    }
} 