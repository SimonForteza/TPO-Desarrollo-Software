    export interface CreateUserRequest {
    username: string;
    email: string;
    password: string;
    ubicacionId: number;
    deporteFavoritoId?: number;
    nivelJuego?: string;
  }