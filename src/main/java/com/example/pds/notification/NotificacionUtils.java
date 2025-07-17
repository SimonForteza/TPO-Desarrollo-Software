package com.example.pds.notification;

import com.example.pds.model.entity.Usuario;
import com.example.pds.model.entity.UsuarioPartido;
import com.example.pds.model.state.PartidoContext;
import com.example.pds.notification.adapter.EmailNotificationAdapter;
import com.example.pds.notification.adapter.PushNotificationAdapter;
import com.example.pds.notification.adapter.INotification;

import java.util.List;

public class NotificacionUtils {
    public static void adjuntarObservers(PartidoContext partidoContext, Usuario usuarioResponsable) {
        List<INotification> metodosNotificacion = List.of(new EmailNotificationAdapter(), new PushNotificationAdapter());
        if (partidoContext.getPartido().getInscripciones() != null) {
            for (UsuarioPartido up : partidoContext.getPartido().getInscripciones()) {
                Usuario inscripto = up.getUsuario();
                if (inscripto != null && !inscripto.equals(usuarioResponsable)) {
                    partidoContext.attach(new Notificador(metodosNotificacion, inscripto));
                }
            }
        }
    }
} 