package com.example.pds.service.implementation;

import com.example.pds.dto.CrearUsuarioDTO;
import com.example.pds.dto.UbicacionDTO;
import com.example.pds.model.entity.Deporte;
import com.example.pds.model.entity.NivelJuego;
import com.example.pds.model.entity.Usuario;
import com.example.pds.model.entity.Ubicacion;
import com.example.pds.repository.DeporteRepository;
import com.example.pds.repository.UsuarioRepository;
import com.example.pds.repository.UbicacionRepository;
import com.example.pds.service.UsuarioService;
import com.example.pds.util.GeocodingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DeporteRepository deporteRepository;

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @Override
    public Usuario crearUsuario(CrearUsuarioDTO dto) {
        if (dto.username() == null || dto.username().isBlank()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
        if (dto.email() == null || dto.email().isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (dto.password() == null || dto.password().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        if (dto.deporteFavorito() == null || dto.deporteFavorito().isBlank()) {
            throw new IllegalArgumentException("El deporte favorito es obligatorio");
        }
        if (dto.nivelJuego() == null || dto.nivelJuego().isBlank()) {
            throw new IllegalArgumentException("El nivel de juego es obligatorio");
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
} 