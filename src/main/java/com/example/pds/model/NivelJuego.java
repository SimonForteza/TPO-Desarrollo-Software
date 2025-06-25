package com.example.pds.model;

public enum NivelJuego {
    PRINCIPIANTE,
    INTERMEDIO,
    PROFESIONAL;

    public static NivelJuego nivelJuegofromString(String nivel) {
        if (nivel == null) {
            throw new IllegalArgumentException("El nivel no puede ser nulo");
        }
        switch (nivel.trim().toUpperCase()) {
            case "PRINCIPIANTE":
                return PRINCIPIANTE;
            case "INTERMEDIO":
                return INTERMEDIO;
            case "PROFESIONAL":
                return PROFESIONAL;
            default:
                throw new IllegalArgumentException("Nivel de juego desconocido: " + nivel);
        }
    }
}
