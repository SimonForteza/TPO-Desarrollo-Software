package com.example.pds.service;

import com.example.pds.dto.CrearUsuarioDTO;
import com.example.pds.model.entity.Usuario;

public interface UsuarioService {
    Usuario crearUsuario(CrearUsuarioDTO dto);
} 