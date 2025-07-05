package com.example.pds.service.implementation;

import com.example.pds.dto.CrearUsuarioDTO;
import com.example.pds.dto.UbicacionDTO;
import com.example.pds.model.entity.Deporte;
import com.example.pds.model.entity.NivelJuego;
import com.example.pds.model.entity.Usuario;
import com.example.pds.model.entity.Ubicacion;
import com.example.pds.model.entity.Partido;
import com.example.pds.model.entity.UsuarioPartido;
import com.example.pds.repository.DeporteRepository;
import com.example.pds.repository.UsuarioRepository;
import com.example.pds.repository.UbicacionRepository;
import com.example.pds.repository.UsuarioPartidoRepository;
import com.example.pds.service.UsuarioService;
import com.example.pds.service.BaseService;
import com.example.pds.util.GeocodingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl extends BaseService implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DeporteRepository deporteRepository;

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @Autowired
    private UsuarioPartidoRepository usuarioPartidoRepository;


    @Override
    public Usuario crearUsuario(CrearUsuarioDTO dto) {
        validateStringNotNullOrEmpty(dto.username(), "nombre de usuario");
        validateStringNotNullOrEmpty(dto.email(), "email");
        validateStringNotNullOrEmpty(dto.password(), "contraseña");
        validateStringNotNullOrEmpty(dto.deporteFavorito(), "deporte favorito");
        validateStringNotNullOrEmpty(dto.nivelJuego(), "nivel de juego");

        // Verificar que no exista ya un usuario con ese email
        if (usuarioRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + dto.email());
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.username());
        usuario.setEmail(dto.email());
        usuario.setPassword(dto.password());

        Deporte deporte = deporteRepository.findByNombre(dto.deporteFavorito())
            .orElseThrow(() -> new IllegalArgumentException("Deporte favorito no encontrado: " + dto.deporteFavorito()));
        usuario.setDeporteFavorito(deporte);

        try {
            usuario.setNivelJuego(NivelJuego.nivelJuegofromString(dto.nivelJuego()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Nivel de juego inválido: " + dto.nivelJuego());
        }

        // Crear y guardar Ubicacion
        Ubicacion ubicacion = null;
        UbicacionDTO ubicacionDTO = dto.ubicacion();
        if (ubicacionDTO != null) {
            // Busca la ubicacion en la API y devuelve [Latitud y Longitud]
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
            usuario.setUbicacion(ubicacion);
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Partido> obtenerPartidosDeUsuario(Long idUsuario) {
        Usuario usuario = repositoryUtils.findByIdOrThrow(usuarioRepository, idUsuario, 
            () -> new IllegalArgumentException("usuario no encontrado con id: " + idUsuario));
        
        List<UsuarioPartido> inscripciones = usuarioPartidoRepository.findByUsuario(usuario);
        
        return inscripciones.stream()
            .map(UsuarioPartido::getPartido)
            .collect(Collectors.toList());
    }
} 