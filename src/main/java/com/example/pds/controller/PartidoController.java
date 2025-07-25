package com.example.pds.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.pds.model.entity.Partido;
import com.example.pds.service.PartidoService;
import com.example.pds.dto.CrearPartidoDTO;
import com.example.pds.model.entity.UsuarioPartido;
import com.example.pds.service.UsuarioPartidoService;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.pds.model.strategyEmparejamiento.TipoEmparejamiento;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/partidos")
public class PartidoController {

    private final PartidoService partidoService;
    private final UsuarioPartidoService usuarioPartidoService;

    @Autowired
    public PartidoController(PartidoService partidoService, UsuarioPartidoService usuarioPartidoService) {
        this.partidoService = partidoService;
        this.usuarioPartidoService = usuarioPartidoService;
    }

    /* ------------------------------ ENDPOINTS ------------------------------ */
    @PostMapping
    public ResponseEntity<?> crearPartido(@RequestBody CrearPartidoDTO crearPartidoDTO) {
        try {
            Partido nuevoPartido = partidoService.crearPartido(crearPartidoDTO);
            return ResponseEntity.ok(nuevoPartido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }

    @PostMapping("/{idPartido}/inscribir/{idUsuario}")
    public ResponseEntity<?> inscribirUsuarioAPartido(@PathVariable Long idPartido, @PathVariable Long idUsuario) {
        try {
            UsuarioPartido inscripcion = usuarioPartidoService.inscribirUsuarioAPartido(idUsuario, idPartido);
            return ResponseEntity.ok(inscripcion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }

    @PostMapping("/{idPartido}/confirmar/{idUsuario}")
    public ResponseEntity<?> confirmarPartido(@PathVariable Long idPartido, @PathVariable Long idUsuario) {
        try {
            UsuarioPartido confirmacion = usuarioPartidoService.confirmarPartido(idUsuario, idPartido);
            return ResponseEntity.ok(confirmacion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }

    @PostMapping("/{idPartido}/finalizar/{idUsuario}")
    public ResponseEntity<?> finalizarPartido(@PathVariable Long idPartido, @PathVariable Long idUsuario) {
        try {
            Partido partido = partidoService.finalizarPartido(idPartido, idUsuario);
            // Validación de creador ya está en el servicio
            return ResponseEntity.ok(partido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }

    @PostMapping("/{idPartido}/cancelar/{idUsuario}")
    public ResponseEntity<?> cancelarPartido(@PathVariable Long idPartido, @PathVariable Long idUsuario) {
        try {
            Partido partido = partidoService.cancelarPartido(idPartido, idUsuario);
            return ResponseEntity.ok(partido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }

    @PostMapping("/{idPartido}/iniciar/{idUsuario}")
    public ResponseEntity<?> iniciarPartido(@PathVariable Long idPartido, @PathVariable Long idUsuario) {
        try {
            Partido partido = partidoService.iniciarPartido(idPartido, idUsuario);
            return ResponseEntity.ok(partido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Partido>> obtenerTodosLosPartidos() {
        List<Partido> partidos = partidoService.obtenerTodosLosPartidos();
        return ResponseEntity.ok(partidos);
    }

    @GetMapping("/emparejados")
    public ResponseEntity<List<Partido>> obtenerPartidosEmparejados(
            @RequestParam Long usuarioId,
            @RequestParam TipoEmparejamiento tipoEmparejamiento) {
        List<Partido> partidos = partidoService.obtenerPartidosEmparejados(usuarioId, tipoEmparejamiento);
        return ResponseEntity.ok(partidos);
    }
    
}
