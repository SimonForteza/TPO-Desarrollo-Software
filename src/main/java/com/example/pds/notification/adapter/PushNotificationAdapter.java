package com.example.pds.notification.adapter;

import com.example.pds.model.entity.Usuario;

public class PushNotificationAdapter implements INotification {
    @Override
    public void notify(Usuario usuario, String mensaje) {
        // Lógica para enviar notificación push usando Firebase
        System.out.println("[Push] Notificación enviada a " + usuario.getEmail() + ": " + mensaje);
    }
} 