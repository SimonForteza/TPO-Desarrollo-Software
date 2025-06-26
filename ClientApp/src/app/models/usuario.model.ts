import { Deporte } from "./deporte.model";
import { NivelJuego } from "./nivel-juego.model";
import { Ubicacion } from "./ubicacion.model";

export interface Usuario {
    id?: number;
    username: string;
    email: string;
    password: string;
    deporteFavorito?: Deporte;
    nivelJuego?: NivelJuego;
    ubicacion: Ubicacion;
  }
