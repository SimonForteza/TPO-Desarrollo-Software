package com.example.pds.model.factory;

import com.example.pds.model.stategyEmparejamiento.EmparejamientoPorCercaniaStrategy;
import com.example.pds.model.stategyEmparejamiento.EmparejamientoPorNivelStrategy;
import com.example.pds.model.stategyEmparejamiento.EmparejamientoStrategy;
import com.example.pds.model.stategyEmparejamiento.TipoEmparejamiento;

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