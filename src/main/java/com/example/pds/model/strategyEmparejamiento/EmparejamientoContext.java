package com.example.pds.model.strategyEmparejamiento;

import com.example.pds.model.entity.Partido;
import com.example.pds.model.entity.Usuario;
import java.util.List;

public class EmparejamientoContext {
    private EmparejamientoStrategy strategy;

    public void setStrategy(EmparejamientoStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Partido> emparejar(Usuario usuario, List<Partido> partidos) {
        if (strategy == null) {
            throw new IllegalStateException("No se ha definido una estrategia de emparejamiento");
        }
        return strategy.emparejarPartidos(usuario, partidos);
    }
} 