package com.example.pds.model.factory;

import com.example.pds.model.strategyEmparejamiento.EmparejamientoPorCercaniaStrategy;
import com.example.pds.model.strategyEmparejamiento.EmparejamientoPorNivelStrategy;
import com.example.pds.model.strategyEmparejamiento.EmparejamientoStrategy;
import com.example.pds.model.strategyEmparejamiento.TipoEmparejamiento;

public class EmparejamientoFactory {
    public static EmparejamientoStrategy crearEstrategia(TipoEmparejamiento tipo) {
        return switch (tipo) {
            case NIVEL -> new EmparejamientoPorNivelStrategy();
            case CERCANIA -> new EmparejamientoPorCercaniaStrategy();
            // case HISTORIAL -> new EmparejamientoPorHistorialStrategy();
            default -> throw new IllegalArgumentException("Tipo de emparejamiento no soportado");
        };
    }
} 