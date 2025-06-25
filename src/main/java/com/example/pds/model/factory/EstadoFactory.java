package com.example.pds.model.factory;

import com.example.pds.model.state.EstadoPartido;
import com.example.pds.model.state.NecesitamosJugadores;
import com.example.pds.model.state.PartidoArmadoState;
import com.example.pds.model.state.ConfirmadoState;
import com.example.pds.model.state.EnJuegoState;
import com.example.pds.model.state.FinalizadoState;
import com.example.pds.model.state.CanceladoState;
import com.example.pds.model.state.PartidoState;


public class EstadoFactory {
    
    /**
     * Crea un estado de partido
     * @param estado
     * @return PartidoState
     */
    public static PartidoState crearEstado(EstadoPartido estado) {
        return switch (estado) {
            case NECESITAMOS_JUGADORES -> new NecesitamosJugadores();
            case PARTIDO_ARMADO -> new PartidoArmadoState();
            case CONFIRMADO -> new ConfirmadoState();
            case EN_JUEGO -> new EnJuegoState();
            case FINALIZADO -> new FinalizadoState();
            case CANCELADO -> new CanceladoState();
        };
    }

    /**
     * Obtiene el estado enum de un estado de partido
     * @param estado
     * @return EstadoPartido
     */
    public static EstadoPartido obtenerEstadoEnum(PartidoState estado) {
        return estado.getEstado();
    }
}
