package com.example.pds.model.state;

import com.example.pds.model.EstadoPartido;

public interface PartidoState {
    void alcanzarNumeroRequerido(PartidoContext partidoContext);
    void confirmar(PartidoContext partidoContext);
    void iniciar(PartidoContext partidoContext);
    void finalizar(PartidoContext partidoContext);
    void cancelar(PartidoContext partidoContext);
    
    EstadoPartido getEstado();
}
