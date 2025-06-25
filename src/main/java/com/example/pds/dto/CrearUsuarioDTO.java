package com.example.pds.dto;

public record CrearUsuarioDTO(
    String username,
    String email,
    String password,
    String deporteFavorito, // nombre del deporte
    String nivelJuego       // String, puede ser null
) {} 