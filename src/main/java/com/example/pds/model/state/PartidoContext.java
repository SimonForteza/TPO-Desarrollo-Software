package com.example.pds.model.state;

import com.example.pds.model.entity.Partido;
import com.example.pds.model.factory.EstadoFactory;
import com.example.pds.notification.IObservable;
import lombok.Data;
import com.example.pds.model.entity.Usuario;
import com.example.pds.model.state.EstadoPartido;

@Data
public class PartidoContext extends IObservable {
    private Partido partido;
    private PartidoState estadoActual;

    public PartidoContext(Partido partido) {    
        this.partido = partido;
        this.estadoActual = EstadoFactory.crearEstado(partido.getEstado());
    }

    public void alcanzarNumeroRequerido(Usuario usuarioResponsable) {
        estadoActual.alcanzarNumeroRequerido(this);
        if (partido.getEstado() == EstadoPartido.PARTIDO_ARMADO) {
            this.notifyObservers(usuarioResponsable);
        }
    }

    public void confirmar(Usuario usuarioResponsable) {
        estadoActual.confirmar(this);
        if (partido.getEstado() == EstadoPartido.CONFIRMADO) {
            this.notifyObservers(usuarioResponsable);
        }
    }

    public void iniciar(Usuario usuarioResponsable) {
        estadoActual.iniciar(this);
        if (partido.getEstado() == EstadoPartido.EN_JUEGO) {
            this.notifyObservers(usuarioResponsable);
        }
    }

    public void finalizar(Usuario usuarioResponsable) {
        estadoActual.finalizar(this);
        if (partido.getEstado() == EstadoPartido.FINALIZADO) {
            this.notifyObservers(usuarioResponsable);
        }
    }

    public void cancelar(Usuario usuarioResponsable) {
        estadoActual.cancelar(this);
        if (partido.getEstado() == EstadoPartido.CANCELADO) {
            this.notifyObservers(usuarioResponsable);
        }
    }

    public void setEstado(PartidoState nuevoEstado) {
        this.estadoActual = nuevoEstado;
        this.partido.setEstado(EstadoFactory.obtenerEstadoEnum(nuevoEstado));
    }
}
