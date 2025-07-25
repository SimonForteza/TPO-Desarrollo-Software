package com.example.pds.model.entity;

import com.example.pds.model.state.EstadoPartido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "partidos")
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

    @ManyToOne
    @JoinColumn(name = "creador_id")
    private Usuario creador;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoPartido estado;

    @OneToMany(mappedBy = "partido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private java.util.List<UsuarioPartido> inscripciones = new java.util.ArrayList<>();

    // Nuevo: configuración de niveles permitidos
    @Column(name = "permitir_cualquier_nivel")
    private boolean permitirCualquierNivel = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_minimo")
    private NivelJuego nivelMinimo;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_maximo")
    private NivelJuego nivelMaximo;

    // Transient: no se guarda en la base de dato
    // Calcular la cantidad de jugadores restantes
    @Transient
    public int getJugadoresRestantes() {
        int cantidadNecesaria = obtenerCantidadJugadores();
        int inscriptos = (inscripciones != null) ? inscripciones.size() : 0;
        return Math.max(0, cantidadNecesaria - inscriptos);
    }

    public int obtenerCantidadInscriptos(){
        return inscripciones.size();
    }

    public int obtenerDuracion(){
        return deporte.getMinutosJuego();
    }

    public int obtenerCantidadJugadores(){
        return deporte.getCantidadJugadores();
    }

    @JsonIgnore
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    @JsonProperty("fecha")
    public LocalDate getFecha() {
        return fechaHora != null ? fechaHora.toLocalDate() : null;
    }

    @JsonProperty("hora")
    public LocalTime getHora() {
        return fechaHora != null ? fechaHora.toLocalTime() : null;
    }
}
