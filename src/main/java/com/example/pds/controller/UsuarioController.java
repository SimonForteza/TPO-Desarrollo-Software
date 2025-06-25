package com.example.pds.controller;

import com.example.pds.dto.CrearUsuarioDTO;
import com.example.pds.model.entity.Usuario;
import com.example.pds.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody CrearUsuarioDTO dto) {
        try {
            Usuario usuario = usuarioService.crearUsuario(dto);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 