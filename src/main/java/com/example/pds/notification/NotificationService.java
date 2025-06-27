package com.example.pds.notification;

import com.example.pds.model.entity.Usuario;
import com.example.pds.model.entity.Partido;
import com.example.pds.model.state.EstadoPartido;
import com.example.pds.notification.adapter.NotificationAdapter;
import com.example.pds.notification.strategy.NotificationStrategy;
import com.example.pds.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService implements NotificationObserver {
    
    private final NotificationManager notificationManager;
    private final List<NotificationStrategy> notificationStrategies;
    private final List<NotificationAdapter> notificationAdapters;
    private final UsuarioRepository usuarioRepository;
    
    @Autowired
    public NotificationService(NotificationManager notificationManager,
                              List<NotificationStrategy> notificationStrategies,
                              List<NotificationAdapter> notificationAdapters,
                              UsuarioRepository usuarioRepository) {
        this.notificationManager = notificationManager;
        this.notificationStrategies = notificationStrategies;
        this.notificationAdapters = notificationAdapters;
        this.usuarioRepository = usuarioRepository;
        
        // Registrarse como observer
        this.notificationManager.registerObserver(this);
    }
    
    @Override
    public void update(NotificationEvent event) {
        // Determinar qué usuarios deben recibir la notificación
        List<Usuario> targetUsers = determineTargetUsers(event);
        
        // Enviar notificaciones usando las estrategias disponibles
        for (Usuario usuario : targetUsers) {
            for (NotificationStrategy strategy : notificationStrategies) {
                strategy.sendNotification(event, usuario);
            }
            
            // También usar los adaptadores
            for (NotificationAdapter adapter : notificationAdapters) {
                if (adapter.supports(event.getType())) {
                    adapter.sendNotification(event, usuario);
                }
            }
        }
    }
    
    private List<Usuario> determineTargetUsers(NotificationEvent event) {
        switch (event.getType()) {
            case NUEVO_PARTIDO:
                // Notificar a usuarios que tienen como deporte favorito el del partido
                return usuarioRepository.findByDeporteFavorito(event.getPartido().getDeporte());
                
            case PARTIDO_ARMADO:
            case PARTIDO_CONFIRMADO:
            case ESTADO_CAMBIADO:
                // Notificar a todos los usuarios inscritos en el partido
                return event.getPartido().getInscripciones().stream()
                    .map(inscripcion -> inscripcion.getUsuario())
                    .toList();
                
            default:
                return List.of();
        }
    }
    
    // Métodos públicos para disparar notificaciones desde otros servicios
    public void notifyNewPartido(Partido partido) {
        notificationManager.notifyNewPartido(partido);
    }
    
    public void notifyPartidoArmado(Partido partido) {
        notificationManager.notifyPartidoArmado(partido);
    }
    
    public void notifyPartidoConfirmado(Partido partido) {
        notificationManager.notifyPartidoConfirmado(partido);
    }
    
    public void notifyEstadoChanged(Partido partido, EstadoPartido previousState) {
        notificationManager.notifyEstadoChanged(partido, previousState);
    }
} 