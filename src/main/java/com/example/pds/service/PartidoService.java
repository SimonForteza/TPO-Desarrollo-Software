package com.example.pds.service;

import com.example.pds.model.Partido;
import com.example.pds.model.Ubicacion;
import com.example.pds.model.Deporte;
import com.example.pds.repository.PartidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartidoService {
    @Autowired
    private PartidoRepository partidoRepository;

    public Partido crearPartido(Partido partido) throws Exception {
        LocalDateTime inicio = partido.getFechaHora();
        int duracion = partido.obtenerDuracion();
        LocalDateTime fin = inicio.plusMinutes(duracion);
        Ubicacion ubicacion = partido.getUbicacion();

        // Buscar partidos en la misma ubicación
        List<Partido> partidosEnUbicacion = partidoRepository.findByUbicacion(ubicacion);
        for (Partido p : partidosEnUbicacion) {
            LocalDateTime inicioExistente = p.getFechaHora();
            int duracionExistente = p.getDeporte().getMinutosJuego();
            LocalDateTime finExistente = inicioExistente.plusMinutes(duracionExistente);
            // Verificar superposición
            if (inicio.isBefore(finExistente) && fin.isAfter(inicioExistente)) {
                throw new Exception("Ya existe un partido en esa ubicación y horario.");
            }
        }
        return partidoRepository.save(partido);
    }
} 