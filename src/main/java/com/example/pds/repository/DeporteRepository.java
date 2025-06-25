package com.example.pds.repository;

import com.example.pds.model.entity.Deporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeporteRepository extends JpaRepository<Deporte, Long> {
    Optional<Deporte> findByNombre(String nombre);
} 