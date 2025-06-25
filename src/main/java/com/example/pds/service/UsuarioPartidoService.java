package com.example.pds.service;


import com.example.pds.model.UsuarioPartido;

public interface UsuarioPartidoService {
    UsuarioPartido inscribirUsuarioAPartido(Long idUsuario, Long idPartido);
    UsuarioPartido confirmarPartido(Long idUsuario, Long idPartido);
} 