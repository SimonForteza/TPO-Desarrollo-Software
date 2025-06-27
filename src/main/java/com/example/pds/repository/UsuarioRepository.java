package com.example.pds.repository;

import com.example.pds.model.entity.Usuario;
import com.example.pds.model.entity.Deporte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByDeporteFavorito(Deporte deporte);
} 