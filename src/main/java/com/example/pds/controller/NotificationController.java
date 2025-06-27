package com.example.pds.controller;

import com.example.pds.notification.NotificationService;
import com.example.pds.model.entity.Partido;
import com.example.pds.model.state.EstadoPartido;
import com.example.pds.repository.PartidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private PartidoRepository partidoRepository;

    @PostMapping("/test/new-partido/{partidoId}")
    public ResponseEntity<String> testNewPartidoNotification(@PathVariable Long partidoId) {
        Partido partido = partidoRepository.findById(partidoId)
            .orElseThrow(() -> new RuntimeException("Partido no encontrado"));
        
        notificationService.notifyNewPartido(partido);
        return ResponseEntity.ok("Notificación de nuevo partido enviada");
    }

    @PostMapping("/test/partido-armado/{partidoId}")
    public ResponseEntity<String> testPartidoArmadoNotification(@PathVariable Long partidoId) {
        Partido partido = partidoRepository.findById(partidoId)
            .orElseThrow(() -> new RuntimeException("Partido no encontrado"));
        
        notificationService.notifyPartidoArmado(partido);
        return ResponseEntity.ok("Notificación de partido armado enviada");
    }

    @PostMapping("/test/partido-confirmado/{partidoId}")
    public ResponseEntity<String> testPartidoConfirmadoNotification(@PathVariable Long partidoId) {
        Partido partido = partidoRepository.findById(partidoId)
            .orElseThrow(() -> new RuntimeException("Partido no encontrado"));
        
        notificationService.notifyPartidoConfirmado(partido);
        return ResponseEntity.ok("Notificación de partido confirmado enviada");
    }

    @PostMapping("/test/estado-changed/{partidoId}")
    public ResponseEntity<String> testEstadoChangedNotification(@PathVariable Long partidoId, 
                                                               @RequestParam EstadoPartido previousState) {
        Partido partido = partidoRepository.findById(partidoId)
            .orElseThrow(() -> new RuntimeException("Partido no encontrado"));
        
        notificationService.notifyEstadoChanged(partido, previousState);
        return ResponseEntity.ok("Notificación de cambio de estado enviada");
    }
} 