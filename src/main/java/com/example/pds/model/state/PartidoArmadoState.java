package com.example.pds.model.state;

public class PartidoArmadoState implements PartidoState{
    @Override
    public void alcanzarNumeroRequerido(PartidoContext partidoContext) {
        throw new IllegalStateException("Ya se alcanzó el número de jugadores requeridos");
    }

    @Override
    public void confirmar(PartidoContext partidoContext) {
        boolean todosAceptaron = partidoContext.getPartido().getInscripciones().stream()
        .allMatch(j -> j.isConfirmado());
        
        if (todosAceptaron) {
            partidoContext.setEstado(new ConfirmadoState());
        }
    }

    @Override
    public void iniciar(PartidoContext partidoContext) {
        throw new IllegalStateException("No se puede iniciar un partido que no se ha confirmado");
    }

    @Override
    public void finalizar(PartidoContext partidoContext) {
        throw new IllegalStateException("No se puede finalizar un partido que no ha comenzado");
    }

    @Override
    public void cancelar(PartidoContext partidoContext) {
        partidoContext.setEstado(new CanceladoState());
    }
    
    @Override
    public EstadoPartido getEstado() {
        return EstadoPartido.PARTIDO_ARMADO;
    }
}
