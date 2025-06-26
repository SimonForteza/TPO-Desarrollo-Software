package com.example.pds.service;

import com.example.pds.model.entity.Partido;
import com.example.pds.dto.CrearPartidoDTO;

public interface PartidoService {
    Partido crearPartido(CrearPartidoDTO crearPartidoDTO);
    //Partido confirmarPartido(Long idPartido);
    Partido finalizarPartido(Long idPartido);
    Partido cancelarPartido(Long idPartido, Long idUsuario);
    //List<Partido> obtenerPartidos();
}
