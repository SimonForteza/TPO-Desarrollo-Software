package com.example.pds.model.stategyEmparejamiento;

import com.example.pds.model.entity.Partido;
import com.example.pds.model.entity.Usuario;
import com.example.pds.model.entity.Ubicacion;
import java.util.List;
import java.util.stream.Collectors;

public class EmparejamientoPorCercaniaStrategy implements EmparejamientoStrategy {
    private static final double RADIO_TIERRA_KM = 6371.0;
    private static final double DISTANCIA_MAX_KM = 10.0;

    @Override
    public List<Partido> emparejarPartidos(Usuario usuario, List<Partido> partidos) {
        // obtener la ubicacion del usuario (latitud y longitud)
        Ubicacion ubicacionUsuario = usuario.getUbicacion();
        if (ubicacionUsuario == null) return List.of();
        double latUsuario = ubicacionUsuario.getLatitud();
        double lonUsuario = ubicacionUsuario.getLongitud();

        // filtrar los partidos que esten a menos de 10km de la ubicacion del usuario
        return partidos.stream()
            .filter(partido -> {
                Ubicacion ubicacionPartido = partido.getUbicacion();
                if (ubicacionPartido == null) return false;
                double latPartido = ubicacionPartido.getLatitud();
                double lonPartido = ubicacionPartido.getLongitud();
                double distancia = calcularDistanciaKm(latUsuario, lonUsuario, latPartido, lonPartido);
                return distancia <= DISTANCIA_MAX_KM;
            })
            .collect(Collectors.toList());
    }

    // calcular la distancia en km entre dos puntos de la tierra (haversine formula)
    private double calcularDistanciaKm(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RADIO_TIERRA_KM * c;
    }
} 