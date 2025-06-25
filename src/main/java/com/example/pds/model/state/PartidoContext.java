package com.example.pds.model.state;

import com.example.pds.model.Partido;
import com.example.pds.model.factory.EstadoFactory;

public class PartidoContext {
    private Partido partido;
    private PartidoState estadoActual;

    public PartidoContext(Partido partido) {    
        this.partido = partido;
        this.estadoActual = EstadoFactory.crearEstado(partido.getEstado());
    }

    public void alcanzarNumeroRequerido() {
        estadoActual.alcanzarNumeroRequerido(this);
    }

    public void confirmar() {
        estadoActual.confirmar(this);
    }

    public void iniciar() {
        estadoActual.iniciar(this);
    }

    public void finalizar() {
        estadoActual.finalizar(this);
    }

    public void cancelar() {
        estadoActual.cancelar(this);
    }

    public void setEstado(PartidoState nuevoEstado) {
        this.estadoActual = nuevoEstado;
        this.partido.setEstado(EstadoFactory.obtenerEstadoEnum(nuevoEstado));
    }
}
