package com.example.pds.notification.adapter;

import com.example.pds.model.entity.Usuario;

public class EmailNotificationAdapter implements INotification {
    @Override
    public void notify(Usuario usuario, String mensaje) {
        // Lógica para enviar email usando JavaMail
        System.out.println("[Email] Notificación enviada a " + usuario.getEmail() + ": " + mensaje);
    }
} 