export interface Notification {
    id?: number
    usuarioId: number
    tipo: TipoNotificacion
    titulo: string
    mensaje: string
    partidoId?: number
    leida: boolean
    fechaCreacion: string
    fechaLectura?: string
  }
  
  export enum TipoNotificacion {
    NUEVO_PARTIDO = "NUEVO_PARTIDO",
    PARTIDO_ARMADO = "PARTIDO_ARMADO",
    PARTIDO_CONFIRMADO = "PARTIDO_CONFIRMADO",
    PARTIDO_EN_JUEGO = "PARTIDO_EN_JUEGO",
    PARTIDO_FINALIZADO = "PARTIDO_FINALIZADO",
    PARTIDO_CANCELADO = "PARTIDO_CANCELADO",
    JUGADOR_UNIDO = "JUGADOR_UNIDO",
    RECORDATORIO = "RECORDATORIO",
  }
  
  export interface CreateNotificationRequest {
    usuarioId: number
    tipo: TipoNotificacion
    titulo: string
    mensaje: string
    partidoId?: number
  }
  
  export interface NotificationSettings {
    usuarioId: number
    pushEnabled: boolean
    emailEnabled: boolean
    nuevoPartido: boolean
    partidoArmado: boolean
    partidoConfirmado: boolean
    cambiosEstado: boolean
    recordatorios: boolean
  }
  