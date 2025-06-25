package com.example.pds.service.implementation;

import com.example.pds.model.EstadoPartido;
import com.example.pds.model.Partido;
import com.example.pds.model.Ubicacion;
import com.example.pds.repository.PartidoRepository;
import com.example.pds.service.PartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartidoServiceImpl implements PartidoService {

    @Autowired
    private PartidoRepository partidoRepository;

    public Partido crearPartido(Partido partido) {
        // Calculo del horario de finalizacion del partido
        LocalDateTime inicio = partido.getFechaHora();
        int duracion = partido.obtenerDuracion();
        LocalDateTime fin = inicio.plusMinutes(duracion);
        // obtener la ubicacion del partido
        Ubicacion ubicacion = partido.getUbicacion();

        // Busca los partidos de esa ubicacion (por nombre de calle y número)
        List<Partido> partidosEnUbicacion = partidoRepository.findByUbicacion_NombreCalleAndUbicacion_Numero(
            ubicacion.getNombreCalle(), ubicacion.getNumero()
        );
        // Verificar si hay superposicion de horarios
        for (Partido p : partidosEnUbicacion) {
            LocalDateTime inicioExistente = p.getFechaHora();
            int duracionExistente = p.obtenerDuracion();
            LocalDateTime finExistente = inicioExistente.plusMinutes(duracionExistente);
            // Verificar superposición
            if (inicio.isBefore(finExistente) && fin.isAfter(inicioExistente)) {
                throw new RuntimeException("Ya existe un partido en esa ubicación y horario.");
            }
        }

        partido.setEstado(EstadoPartido.NECESITAMOS_JUGADORES);
        return partidoRepository.save(partido);
    }

    
} 