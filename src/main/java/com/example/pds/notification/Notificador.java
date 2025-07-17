package com.example.pds.notification;

import com.example.pds.model.entity.Partido;
import com.example.pds.model.entity.Usuario;
import com.example.pds.model.entity.UsuarioPartido;
import com.example.pds.model.state.PartidoContext;
import com.example.pds.notification.adapter.INotification;

import java.util.List;

public class Notificador implements IObserver {
    private final List<INotification> metodosNotificacion;

    public Notificador(List<INotification> metodosNotificacion) {
        this.metodosNotificacion = metodosNotificacion;
    }

    @Override
    public void update(IObservable observable) {
        if (!(observable instanceof PartidoContext)) return;

        PartidoContext context = (PartidoContext) observable; // Casting para obtener el contexto del partido
        Partido partido = context.getPartido(); // Obtener el partido del contexto

        String mensaje = "El partido ha cambiado de estado a: " + partido.getEstado();

        // Recorrer los usuarios inscritos en el partido para notificarles el cambio de estado
        for (UsuarioPartido up : partido.getInscripciones()) {
            Usuario usuario = up.getUsuario();
            for (INotification metodo : metodosNotificacion) {
                metodo.notify(usuario, mensaje);
            }
        }
    }
} 