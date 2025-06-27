package com.example.pds.notification;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationManager implements NotificationSubject {
    private final List<NotificationObserver> observers = new ArrayList<>();

    @Override
    public void registerObserver(NotificationObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(NotificationObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(NotificationEvent event) {
        for (NotificationObserver observer : observers) {
            observer.update(event);
        }
    }

    public void notifyNewPartido(com.example.pds.model.entity.Partido partido) {
        String message = String.format("¡Nuevo partido de %s creado! Fecha: %s, Ubicación: %s", 
            partido.getDeporte().getNombre(), 
            partido.getFecha(), 
            partido.getUbicacion().getNombreCalle() + " " + partido.getUbicacion().getNumero());
        
        NotificationEvent event = new NotificationEvent(
            NotificationEvent.NotificationType.NUEVO_PARTIDO, 
            partido, 
            message
        );
        notifyObservers(event);
    }

    public void notifyPartidoArmado(com.example.pds.model.entity.Partido partido) {
        String message = String.format("¡El partido de %s está armado! Todos los jugadores necesarios se han unido.", 
            partido.getDeporte().getNombre());
        
        NotificationEvent event = new NotificationEvent(
            NotificationEvent.NotificationType.PARTIDO_ARMADO, 
            partido, 
            message
        );
        notifyObservers(event);
    }

    public void notifyPartidoConfirmado(com.example.pds.model.entity.Partido partido) {
        String message = String.format("¡El partido de %s ha sido confirmado! Prepárate para jugar.", 
            partido.getDeporte().getNombre());
        
        NotificationEvent event = new NotificationEvent(
            NotificationEvent.NotificationType.PARTIDO_CONFIRMADO, 
            partido, 
            message
        );
        notifyObservers(event);
    }

    public void notifyEstadoChanged(com.example.pds.model.entity.Partido partido, 
                                   com.example.pds.model.state.EstadoPartido previousState) {
        String message = String.format("El partido de %s cambió de estado de %s a %s", 
            partido.getDeporte().getNombre(), 
            previousState, 
            partido.getEstado());
        
        NotificationEvent event = new NotificationEvent(
            NotificationEvent.NotificationType.ESTADO_CAMBIADO, 
            partido, 
            message, 
            previousState
        );
        notifyObservers(event);
    }
} 