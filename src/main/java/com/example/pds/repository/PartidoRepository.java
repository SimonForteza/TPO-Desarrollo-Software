package com.example.pds.repository;

import com.example.pds.model.entity.Partido;
import com.example.pds.model.entity.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartidoRepository extends JpaRepository<Partido, Long> {
    List<Partido> findByUbicacion(Ubicacion ubicacion);
    List<Partido> findByUbicacion_NombreCalleAndUbicacion_Numero(String nombreCalle, String numero);
} 