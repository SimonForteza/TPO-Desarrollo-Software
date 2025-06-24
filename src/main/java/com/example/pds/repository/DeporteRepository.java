package com.example.pds.repository;

import com.example.pds.model.Deporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeporteRepository extends JpaRepository<Deporte, Long> {
    // Métodos personalizados (si es necesario)
} 