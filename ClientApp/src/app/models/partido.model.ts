export interface Partido {
  id?: number
  tipoDeporte: string
  cantidadJugadores: number
  duracion: number 
  ubicacion: string
  fechaHora: string
  estado: EstadoPartido
  organizadorId?: number
  jugadoresInscritos?: number
  descripcion?: string
  fechaCreacion?: string
  nivelMinimo?: NivelJugador 
  nivelMaximo?: NivelJugador 
  latitud?: number
  longitud?: number
}

export enum EstadoPartido {
  NECESITAMOS_JUGADORES = "Necesitamos jugadores",
  PARTIDO_ARMADO = "Partido armado",
  CONFIRMADO = "Confirmado",
  EN_JUEGO = "En juego",
  FINALIZADO = "Finalizado",
  CANCELADO = "Cancelado",
}

export interface CreatePartidoRequest {
  tipoDeporte: string
  cantidadJugadores: number
  duracion: number
  ubicacion: string
  fechaHora: string
  descripcion?: string
  nivelMinimo?: NivelJugador 
  nivelMaximo?: NivelJugador 
  latitud?: number
  longitud?: number
}

export enum NivelJugador {
  PRINCIPIANTE = "Principiante",
  INTERMEDIO = "Intermedio",
  AVANZADO = "Avanzado",
  EXPERTO = "Experto",
}
