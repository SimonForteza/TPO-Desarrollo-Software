package com.example.pds.notification;

import com.example.pds.model.entity.Partido;
import com.example.pds.model.state.EstadoPartido;

public class NotificationEvent {
    private final NotificationType type;
    private final Partido partido;
    private final String message;
    private final EstadoPartido previousState;

    public NotificationEvent(NotificationType type, Partido partido, String message) {
        this(type, partido, message, null);
    }

    public NotificationEvent(NotificationType type, Partido partido, String message, EstadoPartido previousState) {
        this.type = type;
        this.partido = partido;
        this.message = message;
        this.previousState = previousState;
    }

    public NotificationType getType() {
        return type;
    }

    public Partido getPartido() {
        return partido;
    }

    public String getMessage() {
        return message;
    }

    public EstadoPartido getPreviousState() {
        return previousState;
    }

    public enum NotificationType {
        NUEVO_PARTIDO,
        PARTIDO_ARMADO,
        PARTIDO_CONFIRMADO,
        ESTADO_CAMBIADO
    }
} 