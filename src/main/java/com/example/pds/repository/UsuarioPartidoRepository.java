package com.example.pds.repository;

import com.example.pds.model.UsuarioPartido;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pds.model.Usuario;
import com.example.pds.model.Partido;
import java.util.Optional;

public interface UsuarioPartidoRepository extends JpaRepository<UsuarioPartido, Long> {
    Optional<UsuarioPartido> findByUsuarioAndPartido(Usuario usuario, Partido partido);
    int countByPartido(Partido partido);
} 