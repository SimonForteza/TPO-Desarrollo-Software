import { Deporte } from "./deporte.model";
import { NivelJuego } from "./nivel-juego.model";
import { Ubicacion } from "./ubicacion.model";

  export interface Usuario {
    id?: number
    nombre: string
    apellido: string
    email: string
    telefono: string
    fechaNacimiento: string
    ubicacion: string
    deportesFavoritos: string[]
    nivelesDeporte: NivelDeporte[] 
    fechaRegistro?: string
  }
  
  export interface CreateUserRequest {
    nombre: string
    apellido: string
    email: string
    telefono: string
    fechaNacimiento: string
    ubicacion: string
    deportesFavoritos: string[]
    nivelesDeporte: NivelDeporte[] 
  }
  
  export interface NivelDeporte {
    deporte: string
    nivel: NivelJugador
  }
  
  export enum NivelJugador {
    PRINCIPIANTE = "Principiante",
    INTERMEDIO = "Intermedio",
    AVANZADO = "Avanzado",
    EXPERTO = "Experto",
  }
  