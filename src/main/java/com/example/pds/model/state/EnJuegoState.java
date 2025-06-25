package com.example.pds.model.state;

public class EnJuegoState implements PartidoState{

    @Override
    public void alcanzarNumeroRequerido(PartidoContext partidoContext) {
        throw new IllegalStateException("Ya se alcanzó el número de jugadores requeridos");
    }

    @Override
    public void confirmar(PartidoContext partidoContext) {
        throw new IllegalStateException("ya fue confirmado el partido");
    }

    @Override
    public void iniciar(PartidoContext partidoContext) {
        throw new IllegalStateException("El partido ya se encuentra en curso");
    }

    @Override
    public void finalizar(PartidoContext partidoContext) {
        partidoContext.setEstado(new FinalizadoState());
    }

    @Override
    public void cancelar(PartidoContext partidoContext) {
        partidoContext.setEstado(new CanceladoState());
    }
    
    @Override
    public EstadoPartido getEstado() {
        return EstadoPartido.EN_JUEGO;
    }
}
