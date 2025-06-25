package com.example.pds.model.state;

public class CanceladoState implements PartidoState{

    @Override
    public void alcanzarNumeroRequerido(PartidoContext partidoContext) {
        throw new IllegalStateException("Partido Cancelado");
    }

    @Override
    public void confirmar(PartidoContext partidoContext) {
        throw new IllegalStateException("Partido Cancelado");
    }

    @Override
    public void iniciar(PartidoContext partidoContext) {
        throw new IllegalStateException("Partido Cancelado");
    }

    @Override
    public void finalizar(PartidoContext partidoContext) {
        throw new IllegalStateException("Partido Cancelado");
    }

    @Override
    public void cancelar(PartidoContext partidoContext) {
        throw new IllegalStateException("Partido Cancelado");
    }
    
    @Override
    public EstadoPartido getEstado() {
        return EstadoPartido.CANCELADO;
    }
}
