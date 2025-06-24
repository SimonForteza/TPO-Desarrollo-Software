package com.example.pds.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "partidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Partido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "deporte_id")
    private Deporte deporte;

    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion;

    public int obtenerDuracion(){
        return deporte.getMinutosJuego();
    }

    public int obtenerCantidadJugadores(){
        return deporte.getCantidadJugadores();
    }
}
