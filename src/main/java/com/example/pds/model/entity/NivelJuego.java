package com.example.pds.model.entity;

public enum NivelJuego {
    PRINCIPIANTE(1),
    INTERMEDIO(2),
    PROFESIONAL(3);

    private final int valor;

    NivelJuego(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

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

    public static NivelJuego fromValor(int valor) {
        for (NivelJuego nivel : NivelJuego.values()) {
            if (nivel.getValor() == valor) {
                return nivel;
            }
        }
        throw new IllegalArgumentException("Valor de nivel inválido: " + valor);
    }
}
