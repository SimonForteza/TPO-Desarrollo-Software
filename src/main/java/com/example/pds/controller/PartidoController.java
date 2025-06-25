package com.example.pds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.pds.model.Partido;
import com.example.pds.service.PartidoService;
import com.example.pds.dto.CrearPartidoDTO;

@RestController
@RequestMapping("/partidos")
public class PartidoController {

    private final PartidoService partidoService;

    @Autowired
    public PartidoController(PartidoService partidoService) {
        this.partidoService = partidoService;
    }

    @PostMapping
    public ResponseEntity<Partido> crearPartido(@RequestBody CrearPartidoDTO crearPartidoDTO) {
        Partido nuevoPartido = partidoService.crearPartido(crearPartidoDTO);
        return ResponseEntity.ok(nuevoPartido);
    }
}
