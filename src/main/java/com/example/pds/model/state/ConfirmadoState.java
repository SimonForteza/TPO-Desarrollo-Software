package com.example.pds.model.state;

import com.example.pds.model.EstadoPartido;

public class ConfirmadoState implements PartidoState{
    @Override
    public void alcanzarNumeroRequerido(PartidoContext partidoContext) {
        throw new IllegalStateException("Ya se alcanzó el número de jugadores requeridos");
    }

    @Override
    public void confirmar(PartidoContext partidoContext) {
        throw new IllegalStateException("Ya fue confirmado el partido");
    }

    @Override
    public void iniciar(PartidoContext partidoContext) {
        partidoContext.setEstado(new EnJuegoState());
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
        return EstadoPartido.CONFIRMADO;
    }
}
