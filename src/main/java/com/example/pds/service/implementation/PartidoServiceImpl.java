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

import com.example.pds.dto.CrearPartidoDTO;
import com.example.pds.model.Deporte;
import com.example.pds.model.Usuario;
import com.example.pds.repository.DeporteRepository;
import com.example.pds.repository.UbicacionRepository;
import com.example.pds.repository.UsuarioRepository;
import com.example.pds.service.UsuarioPartidoService;

@Service
public class PartidoServiceImpl implements PartidoService {

    @Autowired
    private PartidoRepository partidoRepository;
    @Autowired
    private DeporteRepository deporteRepository;
    @Autowired
    private UbicacionRepository ubicacionRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioPartidoService usuarioPartidoService;

    public Partido crearPartido(CrearPartidoDTO dto) {
        Deporte deporte = deporteRepository.findByNombre(dto.nombreDeporte())
            .orElseThrow(() -> new RuntimeException("Deporte no encontrado"));

        Ubicacion ubicacion = ubicacionRepository.findById(dto.ubicacionId())
            .orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));
        Usuario creador = usuarioRepository.findById(dto.creadorId())
            .orElseThrow(() -> new RuntimeException("Usuario creador no encontrado"));
        
        // Combinar fecha y hora en LocalDateTime
        java.time.LocalDateTime fechaHora = java.time.LocalDateTime.of(dto.fecha(), dto.hora());

        Partido partido = new Partido();
        partido.setDeporte(deporte);
        partido.setFechaHora(fechaHora);
        partido.setUbicacion(ubicacion);
        partido.setCreador(creador);
        partido.setEstado(EstadoPartido.NECESITAMOS_JUGADORES);

        // Validación de superposición de horarios
        LocalDateTime inicio = partido.getFechaHora();
        int duracion = partido.obtenerDuracion();
        LocalDateTime fin = inicio.plusMinutes(duracion);
        List<Partido> partidosEnUbicacion = partidoRepository.findByUbicacion_NombreCalleAndUbicacion_Numero(
            ubicacion.getNombreCalle(), ubicacion.getNumero()
        );
        for (Partido p : partidosEnUbicacion) {
            LocalDateTime inicioExistente = p.getFechaHora();
            int duracionExistente = p.obtenerDuracion();
            LocalDateTime finExistente = inicioExistente.plusMinutes(duracionExistente);
            if (inicio.isBefore(finExistente) && fin.isAfter(inicioExistente)) {
                throw new RuntimeException("Ya existe un partido en esa ubicación y horario.");
            }
        }
        Partido partidoGuardado = partidoRepository.save(partido);
        usuarioPartidoService.inscribirUsuarioAPartido(creador, partidoGuardado);
        return partidoGuardado;
    }


} 