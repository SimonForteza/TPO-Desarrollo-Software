package com.example.pds.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario_partido")
public class UsuarioPartido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "partido_id")
    @JsonBackReference
    private Partido partido;

    @Column(nullable = false)
    private boolean confirmado; // Indica si el usuario confirmó su participación

    // Puedes agregar más campos si lo necesitas, como fecha de inscripción, estado, etc.
} 