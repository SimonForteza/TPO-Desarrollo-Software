package com.example.pds.service;

import com.example.pds.model.Partido;
import com.example.pds.model.Usuario;
import com.example.pds.model.UsuarioPartido;

public interface UsuarioPartidoService {
    UsuarioPartido inscribirUsuarioAPartido(Usuario usuario, Partido partido);
} 