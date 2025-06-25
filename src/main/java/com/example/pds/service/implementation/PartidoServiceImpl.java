package com.example.pds.service.implementation;

import com.example.pds.model.state.EstadoPartido;
import com.example.pds.model.entity.Partido;
import com.example.pds.model.entity.Ubicacion;
import com.example.pds.model.state.PartidoContext;
import com.example.pds.repository.PartidoRepository;
import com.example.pds.service.PartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.pds.dto.CrearPartidoDTO;
import com.example.pds.model.entity.Deporte;
import com.example.pds.model.entity.Usuario;
import com.example.pds.repository.DeporteRepository;
import com.example.pds.repository.UbicacionRepository;
import com.example.pds.repository.UsuarioRepository;
import com.example.pds.service.UsuarioPartidoService;
import com.example.pds.dto.UbicacionDTO;
import com.example.pds.util.GeocodingUtil;

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

        // Crear y guardar Ubicacion
        Ubicacion ubicacion = null;
        UbicacionDTO ubicacionDTO = dto.ubicacion();
        if (ubicacionDTO != null) {
            double[] coords = GeocodingUtil.getLatLongFromAddress(
                ubicacionDTO.nombreCalle(),
                ubicacionDTO.numero(),
                ubicacionDTO.ciudad()
            );
            double lat = coords != null ? coords[0] : 0.0;
            double lon = coords != null ? coords[1] : 0.0;
            ubicacion = new Ubicacion(
                ubicacionDTO.nombreCalle(),
                ubicacionDTO.numero(),
                ubicacionDTO.ciudad(),
                lon,
                lat
            );
            ubicacion = ubicacionRepository.save(ubicacion);
        }

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
        usuarioPartidoService.inscribirUsuarioAPartido(creador.getId(), partidoGuardado.getId());
        return partidoGuardado;
    }

    @Override
    public Partido finalizarPartido(Long idPartido) {
        if (idPartido == null) {
            throw new IllegalArgumentException("El ID del partido no puede ser nulo");
        }

        Partido partido = partidoRepository.findById(idPartido)
            .orElseThrow(() -> new RuntimeException("Partido no encontrado"));

        PartidoContext context = new PartidoContext(partido);
        context.finalizar();

        return partidoRepository.save(context.getPartido());
    }

    @Override
    public Partido cancelarPartido(Long idPartido) {
        if (idPartido == null) {
            throw new IllegalArgumentException("El ID del partido no puede ser nulo");
        }

        Partido partido = partidoRepository.findById(idPartido)
                .orElseThrow(() -> new RuntimeException("Partido no encontrado"));

        PartidoContext context = new PartidoContext(partido);
        context.cancelar();

        return partidoRepository.save(context.getPartido());
    }


} 