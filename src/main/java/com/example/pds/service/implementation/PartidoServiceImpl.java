package com.example.pds.service.implementation;

import com.example.pds.model.state.EstadoPartido;
import com.example.pds.model.entity.Partido;
import com.example.pds.model.entity.Ubicacion;
import com.example.pds.model.state.PartidoContext;
import com.example.pds.model.strategyEmparejamiento.EmparejamientoStrategy;
import com.example.pds.repository.PartidoRepository;
import com.example.pds.service.PartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.pds.dto.CrearPartidoDTO;
import com.example.pds.model.entity.Deporte;
import com.example.pds.model.entity.NivelJuego;
import com.example.pds.model.entity.Usuario;
import com.example.pds.repository.DeporteRepository;
import com.example.pds.repository.UbicacionRepository;
import com.example.pds.repository.UsuarioRepository;
import com.example.pds.service.UsuarioPartidoService;
import com.example.pds.dto.UbicacionDTO;
import com.example.pds.util.GeocodingUtil;
import com.example.pds.model.strategyEmparejamiento.EmparejamientoContext;
import com.example.pds.model.strategyEmparejamiento.TipoEmparejamiento;
import com.example.pds.model.factory.EmparejamientoFactory;
import com.example.pds.service.UsuarioService;
import com.example.pds.service.BaseService;


@Service
public class PartidoServiceImpl extends BaseService implements PartidoService {

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
    @Autowired
    private UsuarioService usuarioService;


    // estrategia de emparejamiento
    private EmparejamientoStrategy estrategia;

    public Partido crearPartido(CrearPartidoDTO dto) {
        validateStringNotNullOrEmpty(dto.nombreDeporte(), "nombre del deporte");
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

        Usuario creador = repositoryUtils.findByIdOrThrow(usuarioRepository, dto.creadorId(), 
            () -> new RuntimeException("Usuario creador no encontrado"));
        
        // Combinar fecha y hora en LocalDateTime
        java.time.LocalDateTime fechaHora = java.time.LocalDateTime.of(dto.fecha(), dto.hora());

        Partido partido = new Partido();
        partido.setDeporte(deporte);
        partido.setFechaHora(fechaHora);
        partido.setUbicacion(ubicacion);
        partido.setCreador(creador);
        partido.setEstado(EstadoPartido.NECESITAMOS_JUGADORES);

        // seteamos los niveles de juego si partido no es de cualquier nivel
        partido.setPermitirCualquierNivel(dto.permitirCualquierNivel());
        if (!dto.permitirCualquierNivel()) {
            if (dto.nivelMinimo() != null && !dto.nivelMinimo().isBlank()) {
                partido.setNivelMinimo(NivelJuego.nivelJuegofromString(dto.nivelMinimo()));
            }
            if (dto.nivelMaximo() != null && !dto.nivelMaximo().isBlank()) {
                partido.setNivelMaximo(NivelJuego.nivelJuegofromString(dto.nivelMaximo()));
            }
        } else {
            partido.setNivelMinimo(null);
            partido.setNivelMaximo(null);
        }

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
        // Volver a buscar el partido para que traiga las inscripciones actualizadas
        return repositoryUtils.findByIdOrThrow(partidoRepository, partidoGuardado.getId(), 
            () -> new RuntimeException("Partido no encontrado"));
    }

    @Override
    public Partido iniciarPartido(Long idPartido, Long idUsuario) {
        repositoryUtils.validateIdNotNull(idPartido, "partido");
        repositoryUtils.validateIdNotNull(idUsuario, "usuario");
        Partido partido = repositoryUtils.findByIdOrThrow(partidoRepository, idPartido, 
            () -> new RuntimeException("Partido no encontrado"));
        // Verificar si el usuario es el creador
        if (partido.getCreador() == null || !partido.getCreador().getId().equals(idUsuario)) {
            throw new IllegalArgumentException("Solo el creador del partido puede iniciarlo");
        }
        PartidoContext context = new PartidoContext(partido);
        context.iniciar();
        return partidoRepository.save(context.getPartido());
    }

    @Override
    public Partido finalizarPartido(Long idPartido, Long idUsuario) {
        repositoryUtils.validateIdNotNull(idPartido, "partido");
        repositoryUtils.validateIdNotNull(idUsuario, "usuario");
        Partido partido = repositoryUtils.findByIdOrThrow(partidoRepository, idPartido, 
            () -> new RuntimeException("Partido no encontrado"));
        // Verificar si el usuario es el creador
        if (partido.getCreador() == null || !partido.getCreador().getId().equals(idUsuario)) {
            throw new IllegalArgumentException("Solo el creador del partido puede finalizarlo");
        }
        PartidoContext context = new PartidoContext(partido);
        context.finalizar();
        return partidoRepository.save(context.getPartido());
    }

    @Override
    public Partido cancelarPartido(Long idPartido, Long idUsuario) {
        repositoryUtils.validateIdNotNull(idPartido, "partido");
        repositoryUtils.validateIdNotNull(idUsuario, "usuario");

        Partido partido = repositoryUtils.findByIdOrThrow(partidoRepository, idPartido, 
            () -> new RuntimeException("Partido no encontrado"));

        // Verificar si el usuario es el creador
        if (partido.getCreador() == null || !partido.getCreador().getId().equals(idUsuario)) {
            throw new IllegalArgumentException("Solo el creador del partido puede cancelarlo");
        }

        PartidoContext context = new PartidoContext(partido);
        context.cancelar();

        return partidoRepository.save(context.getPartido());
    }

    @Override
    public List<Partido> obtenerTodosLosPartidos() {
        return partidoRepository.findByEstadoNot(EstadoPartido.CANCELADO);
    }

    @Override
    public List<Partido> obtenerPartidosEmparejados(Long usuarioId, TipoEmparejamiento tipoEmparejamiento) {
        Usuario usuario = repositoryUtils.findByIdOrThrow(usuarioRepository, usuarioId, 
            () -> new RuntimeException("Usuario no encontrado"));
        List<Partido> partidos = partidoRepository.findByEstadoNot(EstadoPartido.CANCELADO);

        EmparejamientoContext contexto = new EmparejamientoContext();
        if (tipoEmparejamiento == TipoEmparejamiento.HISTORIAL) {
            contexto.setStrategy(EmparejamientoFactory.crearEstrategia(tipoEmparejamiento, usuarioService));
        } else {
            contexto.setStrategy(EmparejamientoFactory.crearEstrategia(tipoEmparejamiento, null));
        }
        return contexto.emparejar(usuario, partidos);
    }

    

} 