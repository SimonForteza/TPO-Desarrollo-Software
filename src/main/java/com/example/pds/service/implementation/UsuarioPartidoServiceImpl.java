package com.example.pds.service.implementation;

import com.example.pds.model.entity.Partido;
import com.example.pds.model.entity.Usuario;
import com.example.pds.model.entity.UsuarioPartido;
import com.example.pds.model.state.PartidoContext;
import com.example.pds.repository.UsuarioPartidoRepository;
import com.example.pds.service.UsuarioPartidoService;
import com.example.pds.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.example.pds.model.state.EstadoPartido;
import com.example.pds.repository.UsuarioRepository;
import com.example.pds.repository.PartidoRepository;
import com.example.pds.notification.NotificacionUtils;

@Service
public class UsuarioPartidoServiceImpl extends BaseService implements UsuarioPartidoService {

    @Autowired
    private UsuarioPartidoRepository usuarioPartidoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PartidoRepository partidoRepository;


    @Override
    public UsuarioPartido inscribirUsuarioAPartido(Long idUsuario, Long idPartido) {
        Usuario usuario = repositoryUtils.findByIdOrThrow(usuarioRepository, idUsuario, 
            () -> new IllegalArgumentException("usuario no encontrado con id: " + idUsuario));
        Partido partido = repositoryUtils.findByIdOrThrow(partidoRepository, idPartido, 
            () -> new IllegalArgumentException("partido no encontrado con id: " + idPartido));

        // Validar si el usuario ya está inscripto
        Optional<UsuarioPartido> existente = usuarioPartidoRepository.findByUsuarioAndPartido(usuario, partido);
        if (existente.isPresent()) {
            throw new IllegalArgumentException("El usuario ya está inscripto en este partido.");
        }
        // Validar nivel de juego si el partido lo requiere
        if (!partido.isPermitirCualquierNivel()) {
            if (usuario.getNivelJuego() == null) {
                throw new IllegalArgumentException("El usuario no tiene nivel de juego asignado. Por favor, actualice su perfil con un nivel de juego para poder inscribirse a este partido.");
            }
            if (partido.getNivelMinimo() != null && usuario.getNivelJuego().getValor() < partido.getNivelMinimo().getValor()) {
                throw new IllegalArgumentException("No puedes inscribirte a este partido porque tu nivel de juego ('" + usuario.getNivelJuego().name() + "') es menor al mínimo requerido ('" + partido.getNivelMinimo().name() + "').");
            }
            if (partido.getNivelMaximo() != null && usuario.getNivelJuego().getValor() > partido.getNivelMaximo().getValor()) {
                throw new IllegalArgumentException("No puedes inscribirte a este partido porque tu nivel de juego ('" + usuario.getNivelJuego().name() + "') es mayor al máximo permitido ('" + partido.getNivelMaximo().name() + "').");
            }
        }
        // Validar estado del partido
        if (partido.getEstado() != EstadoPartido.NECESITAMOS_JUGADORES) {
            throw new IllegalArgumentException("No se puede inscribir a este partido en el estado actual: " + partido.getEstado());
        }
        // Validar cupo
        int inscriptos = usuarioPartidoRepository.countByPartido(partido);
        int cupo = partido.obtenerCantidadJugadores();
        if (inscriptos >= cupo) {
            throw new IllegalArgumentException("El partido ya alcanzó el cupo máximo de jugadores.");
        }
        // Inscribir
        UsuarioPartido usuarioPartido = new UsuarioPartido();
        usuarioPartido.setUsuario(usuario);
        usuarioPartido.setPartido(partido);
        usuarioPartido.setConfirmado(false); // Por defecto no confirmado
        usuarioPartido = usuarioPartidoRepository.save(usuarioPartido);

        // Recargar el partido para obtener las inscripciones actualizadas
        partido = repositoryUtils.findByIdOrThrow(partidoRepository, idPartido, 
            () -> new IllegalArgumentException("partido no encontrado con id: " + idPartido));

        PartidoContext partidoContext = new PartidoContext(partido);
        NotificacionUtils.adjuntarObservers(partidoContext, usuario);
        // Actualizar estado del partido usando el patrón State
        partidoContext.alcanzarNumeroRequerido(usuario);
        
        // Guardar el partido con el estado actualizado
        partidoRepository.save(partido);
        
        return usuarioPartido;
    }

    @Override
    public UsuarioPartido confirmarPartido(Long idUsuario, Long idPartido) {
        Usuario usuario = repositoryUtils.findByIdOrThrow(usuarioRepository, idUsuario, 
            () -> new IllegalArgumentException("usuario no encontrado con id: " + idUsuario));
        Partido partido = repositoryUtils.findByIdOrThrow(partidoRepository, idPartido, 
            () -> new IllegalArgumentException("partido no encontrado con id: " + idPartido));

        UsuarioPartido usuarioPartido = usuarioPartidoRepository.findByUsuarioAndPartido(usuario, partido)
            .orElseThrow(() -> new IllegalArgumentException("El usuario no está inscripto en este partido."));

        // Validar si el usuario ya confirmó el partido
        if (usuarioPartido.isConfirmado()) {
            throw new IllegalArgumentException("El usuario ya confirmó su participación en este partido.");
        }

        // Confirmar al usuario
        usuarioPartido.setConfirmado(true);
        usuarioPartido = usuarioPartidoRepository.save(usuarioPartido);

        // Recargar el partido para obtener las confirmaciones actualizadas
        partido = repositoryUtils.findByIdOrThrow(partidoRepository, idPartido, 
            () -> new IllegalArgumentException("partido no encontrado con id: " + idPartido));

        PartidoContext context = new PartidoContext(partido);
        NotificacionUtils.adjuntarObservers(context, usuario);
        // Actualizar estado del partido usando el contexto
        if (partido.getEstado() == EstadoPartido.NECESITAMOS_JUGADORES) {
            context.alcanzarNumeroRequerido(usuario);
        }
        
        // Si está en PARTIDO_ARMADO, verificar si todos confirmaron
        if (partido.getEstado() == EstadoPartido.PARTIDO_ARMADO) {
            context.confirmar(usuario);
        }
        
        // Guardar el partido con el estado actualizado
        partidoRepository.save(context.getPartido());
        
        return usuarioPartido;
    }
} 