package com.example.pds.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "deportes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Deporte {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "cantidad_jugadores", nullable = false)
    private int cantidadJugadores;
    
    @Column(name = "minutos_juego", nullable = false)
    private int minutosJuego;
}
