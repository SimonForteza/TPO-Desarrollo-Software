package com.example.pds.repository;

import com.example.pds.model.Partido;
import com.example.pds.model.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartidoRepository extends JpaRepository<Partido, Long> {
    List<Partido> findByUbicacion(Ubicacion ubicacion);
    List<Partido> findByUbicacion_NombreCalleAndUbicacion_Numero(String nombreCalle, String numero);
} 