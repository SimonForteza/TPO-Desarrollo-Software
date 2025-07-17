package com.example.pds.notification;

import com.example.pds.model.entity.Usuario;

public interface IObserver {
    void update(IObservable observable, Usuario usuarioResponsable);
} 