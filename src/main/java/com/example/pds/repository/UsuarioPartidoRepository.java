package com.example.pds.repository;

import com.example.pds.model.entity.UsuarioPartido;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pds.model.entity.Usuario;
import com.example.pds.model.entity.Partido;
import java.util.Optional;
import java.util.List;

public interface UsuarioPartidoRepository extends JpaRepository<UsuarioPartido, Long> {
    Optional<UsuarioPartido> findByUsuarioAndPartido(Usuario usuario, Partido partido);
    int countByPartido(Partido partido);
    List<UsuarioPartido> findByUsuario(Usuario usuario);
} 