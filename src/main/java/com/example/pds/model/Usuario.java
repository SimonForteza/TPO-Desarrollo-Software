package com.example.pds.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name = "deporte_favorito_id")
    private Deporte deporteFavorito; // Opcional

    @Enumerated(EnumType.STRING)
    private NivelJuego nivelJuego;    // Opcional

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private java.util.List<UsuarioPartido> partidosInscriptos = new java.util.ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion;
}
