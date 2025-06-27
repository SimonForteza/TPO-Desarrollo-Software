package com.example.pds.service;

import com.example.pds.dto.CrearUsuarioDTO;
import com.example.pds.model.entity.Usuario;
import com.example.pds.model.entity.Partido;
import java.util.List;

public interface UsuarioService {
    Usuario crearUsuario(CrearUsuarioDTO dto);
    List<Partido> obtenerPartidosDeUsuario(Long idUsuario);
} 