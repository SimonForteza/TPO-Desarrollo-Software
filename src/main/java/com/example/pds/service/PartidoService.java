package com.example.pds.service;

import com.example.pds.model.entity.Partido;
import com.example.pds.dto.CrearPartidoDTO;
import java.util.List;
import com.example.pds.model.strategyEmparejamiento.TipoEmparejamiento;

public interface PartidoService {
    Partido crearPartido(CrearPartidoDTO crearPartidoDTO);
    Partido iniciarPartido(Long idPartido, Long idUsuario);
    Partido finalizarPartido(Long idPartido, Long idUsuario);
    Partido cancelarPartido(Long idPartido, Long idUsuario);
    List<Partido> obtenerTodosLosPartidos();

    List<Partido> obtenerPartidosEmparejados(Long usuarioId, TipoEmparejamiento tipoEmparejamiento);

}
