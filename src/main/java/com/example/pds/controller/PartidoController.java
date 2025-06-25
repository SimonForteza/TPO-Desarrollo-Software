package com.example.pds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.pds.model.entity.Partido;
import com.example.pds.service.PartidoService;
import com.example.pds.dto.CrearPartidoDTO;
import com.example.pds.model.entity.UsuarioPartido;
import com.example.pds.service.UsuarioService;
import com.example.pds.service.UsuarioPartidoService;

@RestController
@RequestMapping("/partidos")
public class PartidoController {

    private final PartidoService partidoService;
    private final UsuarioService usuarioService;
    private final UsuarioPartidoService usuarioPartidoService;

    @Autowired
    public PartidoController(PartidoService partidoService, UsuarioService usuarioService, UsuarioPartidoService usuarioPartidoService) {
        this.partidoService = partidoService;
        this.usuarioService = usuarioService;
        this.usuarioPartidoService = usuarioPartidoService;
    }

    @PostMapping
    public ResponseEntity<Partido> crearPartido(@RequestBody CrearPartidoDTO crearPartidoDTO) {
        Partido nuevoPartido = partidoService.crearPartido(crearPartidoDTO);
        return ResponseEntity.ok(nuevoPartido);
    }

    @PostMapping("/inscribir")
    public ResponseEntity<?> inscribirUsuarioAPartido(@RequestParam Long idUsuario, @RequestParam Long idPartido) {
        try {
            UsuarioPartido inscripcion = usuarioPartidoService.inscribirUsuarioAPartido(idUsuario, idPartido);
            return ResponseEntity.ok(inscripcion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }
}
