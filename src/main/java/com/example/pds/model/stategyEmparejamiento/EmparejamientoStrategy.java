package com.example.pds.model.stategyEmparejamiento;

import com.example.pds.model.entity.Partido;
import com.example.pds.model.entity.Usuario;
import java.util.List;

public interface EmparejamientoStrategy {
    List<Partido> emparejarPartidos(Usuario usuario, List<Partido> partidos);
}
