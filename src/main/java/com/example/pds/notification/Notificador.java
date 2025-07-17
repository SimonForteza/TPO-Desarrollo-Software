package com.example.pds.notification;

import com.example.pds.model.entity.Partido;
import com.example.pds.model.entity.Usuario;
import com.example.pds.model.state.PartidoContext;
import com.example.pds.notification.adapter.INotification;

import java.util.List;

public class Notificador implements IObserver {
    private final List<INotification> metodosNotificacion;
    private final Usuario usuario;

    public Notificador(List<INotification> metodosNotificacion, Usuario usuario) {
        this.metodosNotificacion = metodosNotificacion;
        this.usuario = usuario;
    }

    @Override
    public void update(IObservable observable, Usuario usuarioResponsable) {
        if (!(observable instanceof PartidoContext)) return;

        PartidoContext context = (PartidoContext) observable; // Casting para obtener el contexto del partido
        Partido partido = context.getPartido(); // Obtener el partido del contexto

        if (usuario.equals(usuarioResponsable)) return; // No notificar al responsable

        String mensaje = "El partido ha cambiado de estado a: " + partido.getEstado();
        for (INotification metodo : metodosNotificacion) {
            metodo.notify(usuario, mensaje);
        }
    }
} 