package com.example.pds.model.strategyEmparejamiento;

import com.example.pds.model.entity.Partido;
import com.example.pds.model.entity.Usuario;
import com.example.pds.model.entity.UsuarioPartido;
import com.example.pds.service.UsuarioService;
import java.util.*;
import java.util.stream.Collectors;

public class EmparejamientoPorHistorialStrategy implements EmparejamientoStrategy {
    private final UsuarioService usuarioService;

    public EmparejamientoPorHistorialStrategy(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public List<Partido> emparejarPartidos(Usuario usuario, List<Partido> partidos) {
        // Obtener historial de partidos del usuario
        List<Partido> historial = usuarioService.obtenerPartidosDeUsuario(usuario.getId());
        // Obtener todos los usuarios con los que ya jugó
        Set<Long> conocidos = new HashSet<>();
        for (Partido p : historial) {
            for (UsuarioPartido up : p.getInscripciones()) {
                if (!up.obtenerIdUsuario().equals(usuario.getId())) {
                    conocidos.add(up.obtenerIdUsuario());
                }
            }
        }
        // Ordenar partidos por cantidad de usuarios conocidos
        return partidos.stream()
            .sorted((p1, p2) -> {
                long conocidosP1 = p1.getInscripciones().stream().filter(up -> conocidos.contains(up.obtenerIdUsuario())).count();
                long conocidosP2 = p2.getInscripciones().stream().filter(up -> conocidos.contains(up.obtenerIdUsuario())).count();
                return Long.compare(conocidosP2, conocidosP1); // Descendente
            })
            .collect(Collectors.toList());
    }
} 