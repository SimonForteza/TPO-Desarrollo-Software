package com.example.pds.notification.adapter;

import com.example.pds.model.entity.Usuario;

public interface INotification {
    void notify(Usuario usuario, String mensaje);
} 