package com.example.pds.service;

import com.example.pds.model.Deporte;
import com.example.pds.repository.DeporteRepository;
import java.util.List;

public interface DeporteService {
    Deporte crearDeporte(Deporte deporte);
    List<Deporte> obtenerTodosLosDeportes();
    void eliminarDeportePorId(Long id);
}
