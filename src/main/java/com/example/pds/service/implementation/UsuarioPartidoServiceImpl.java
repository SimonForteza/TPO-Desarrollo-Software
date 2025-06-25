package com.example.pds.service.implementation;

import com.example.pds.model.Partido;
import com.example.pds.model.Usuario;
import com.example.pds.model.UsuarioPartido;
import com.example.pds.repository.UsuarioPartidoRepository;
import com.example.pds.service.UsuarioPartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioPartidoServiceImpl implements UsuarioPartidoService {

    @Autowired
    private UsuarioPartidoRepository usuarioPartidoRepository;

    @Override
    public UsuarioPartido inscribirUsuarioAPartido(Usuario usuario, Partido partido) {
        UsuarioPartido usuarioPartido = new UsuarioPartido();
        usuarioPartido.setUsuario(usuario);
        usuarioPartido.setPartido(partido);
        return usuarioPartidoRepository.save(usuarioPartido);
    }
} 