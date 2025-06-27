package com.example.pds.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CrearPartidoDTO(
    String nombreDeporte,
    LocalDate fecha,
    LocalTime hora,
    UbicacionDTO ubicacion,
    Long creadorId,
    boolean permitirCualquierNivel,
    String nivelMinimo,
    String nivelMaximo
) {} 