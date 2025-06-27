package com.example.pds.model.stategyEmparejamiento;

import com.example.pds.model.entity.Partido;
import com.example.pds.model.entity.Usuario;
import com.example.pds.model.entity.NivelJuego;
import java.util.List;
import java.util.stream.Collectors;

public class EmparejamientoPorNivelStrategy implements EmparejamientoStrategy {
    @Override
    public List<Partido> emparejarPartidos(Usuario usuario, List<Partido> partidos) {
        // obtener el nivel de juego del usuario
        NivelJuego nivelUsuario = usuario.getNivelJuego();

        // filtrar los partidos que tengan el mismo nivel de juego que el usuario
        return partidos.stream()
            .filter(partido -> {
                if (partido.isPermitirCualquierNivel()) {
                    return true;
                }
                NivelJuego min = partido.getNivelMinimo();
                NivelJuego max = partido.getNivelMaximo();
                int valorUsuario = nivelUsuario != null ? nivelUsuario.getValor() : 0;
                int valorMin = min != null ? min.getValor() : Integer.MIN_VALUE;
                int valorMax = max != null ? max.getValor() : Integer.MAX_VALUE;
                return valorUsuario >= valorMin && valorUsuario <= valorMax;
            })
            .collect(Collectors.toList());
    }
} 