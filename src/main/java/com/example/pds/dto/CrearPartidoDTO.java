package com.example.pds.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CrearPartidoDTO(
    String nombreDeporte,
    LocalDate fecha,
    LocalTime hora,
    // TODO: mejorar la validacion de la ubicacion
    Long ubicacionId,
    Long creadorId
) {} 