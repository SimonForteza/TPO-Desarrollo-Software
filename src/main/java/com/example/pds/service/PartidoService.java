package com.example.pds.service;

import java.util.List;

import com.example.pds.model.Partido;
import com.example.pds.dto.CrearPartidoDTO;

public interface PartidoService {
    Partido crearPartido(CrearPartidoDTO crearPartidoDTO);
    //Partido confirmarPartido(Long idPartido);
    Partido finalizarPartido(Long idPartido);
    Partido cancelarPartido(Long idPartido);
    //List<Partido> obtenerPartidos();
}
