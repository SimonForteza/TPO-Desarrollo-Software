package com.example.pds.model.state;

import com.example.pds.model.EstadoPartido;

public class NecesitamosJugadores implements PartidoState {

    @Override
    public void alcanzarNumeroRequerido(PartidoContext partidoContext) {
        if (partidoContext.getPartido().obtenerCantidadInscriptos() >= partidoContext.getPartido().obtenerCantidadJugadores()) {
            partidoContext.setEstado(new PartidoArmadoState());
        }
    }

    @Override
    public void confirmar(PartidoContext partidoContext) {
        throw new IllegalStateException("No se alcanzó la cantidad mínima de jugadores para confirmar el partido");
    }

    @Override
    public void iniciar(PartidoContext partidoContext) {
        throw new IllegalStateException("Faltan jugadores para completar el partido. No se puede iniciar");
    }

    @Override
    public void finalizar(PartidoContext partidoContext) {
        throw new IllegalStateException("No se puede finalizar un partido que no se ha iniciado");
    }

    @Override
    public void cancelar(PartidoContext partidoContext) {
        partidoContext.setEstado(new CanceladoState());
    }
    
    @Override
    public EstadoPartido getEstado() {
        return EstadoPartido.NECESITAMOS_JUGADORES;
    }
}
    
