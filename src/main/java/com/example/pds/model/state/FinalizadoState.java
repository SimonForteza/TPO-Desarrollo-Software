package com.example.pds.model.state;

public class FinalizadoState implements PartidoState{
    @Override
    public void alcanzarNumeroRequerido(PartidoContext partidoContext) {
        throw new IllegalStateException("Partido finalizado");
    }

    @Override
    public void confirmar(PartidoContext partidoContext) {
        throw new IllegalStateException("Partido finalizado");
    }

    @Override
    public void iniciar(PartidoContext partidoContext) {
        throw new IllegalStateException("Partido finalizado");
    }

    @Override
    public void finalizar(PartidoContext partidoContext) {
        throw new IllegalStateException("Partido finalizado");
    }

    @Override
    public void cancelar(PartidoContext partidoContext) {
        throw new IllegalStateException("Partido finalizado");
    }
    
    @Override
    public EstadoPartido getEstado() {
        return EstadoPartido.FINALIZADO;
    }
}
