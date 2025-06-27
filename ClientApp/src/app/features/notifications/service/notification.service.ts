import { Injectable } from "@angular/core"
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { Observable, BehaviorSubject } from "rxjs"
import { environment } from "../../../../environment/environment"
import {
  type Notification,
  type CreateNotificationRequest,
  TipoNotificacion,
  type NotificationSettings,
} from "../../../models/notification.model"
import type { Partido } from "../../../models/partido.model"

@Injectable({
  providedIn: "root",
})
export class NotificationService {
  private apiUrl = `${environment.apiUrl}/notifications`
  private notificationsSubject = new BehaviorSubject<Notification[]>([])
  public notifications$ = this.notificationsSubject.asObservable()

  private unreadCountSubject = new BehaviorSubject<number>(0)
  public unreadCount$ = this.unreadCountSubject.asObservable()

  constructor(private http: HttpClient) {
    this.initializeNotifications()
  }

  private initializeNotifications(): void {
    // Simular algunas notificaciones iniciales
    const mockNotifications: Notification[] = [
      {
        id: 1,
        usuarioId: 1,
        tipo: TipoNotificacion.NUEVO_PARTIDO,
        titulo: "¡Nuevo partido de Fútbol!",
        mensaje: "Se creó un nuevo partido de Fútbol para mañana a las 19:00 en Cancha Norte",
        partidoId: 1,
        leida: false,
        fechaCreacion: new Date().toISOString(),
      },
      {
        id: 2,
        usuarioId: 1,
        tipo: TipoNotificacion.PARTIDO_ARMADO,
        titulo: "¡Partido armado!",
        mensaje: "El partido de Básquet ya tiene suficientes jugadores",
        partidoId: 2,
        leida: false,
        fechaCreacion: new Date(Date.now() - 3600000).toISOString(),
      },
    ]

    this.notificationsSubject.next(mockNotifications)
    this.updateUnreadCount()
  }


  getUserNotifications(usuarioId: number): Observable<Notification[]> {
    return this.http.get<Notification[]>(`${this.apiUrl}/user/${usuarioId}`)
  }


  createNotification(notification: CreateNotificationRequest): Observable<Notification> {
    const headers = new HttpHeaders({
      "Content-Type": "application/json",
    })
    return this.http.post<Notification>(this.apiUrl, notification, { headers })
  }


  markAsRead(notificationId: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/${notificationId}/read`, {})
  }


  markAllAsRead(usuarioId: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/user/${usuarioId}/read-all`, {})
  }


  deleteNotification(notificationId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${notificationId}`)
  }


  getNotificationSettings(usuarioId: number): Observable<NotificationSettings> {
    return this.http.get<NotificationSettings>(`${this.apiUrl}/settings/${usuarioId}`)
  }


  updateNotificationSettings(settings: NotificationSettings): Observable<NotificationSettings> {
    const headers = new HttpHeaders({
      "Content-Type": "application/json",
    })
    return this.http.put<NotificationSettings>(`${this.apiUrl}/settings`, settings, { headers })
  }

  
  notifyNuevoPartido(usuarioId: number, partido: Partido): void {
    const notification: CreateNotificationRequest = {
      usuarioId,
      tipo: TipoNotificacion.NUEVO_PARTIDO,
      titulo: `¡Nuevo partido de ${partido.tipoDeporte}!`,
      mensaje: `Se creó un nuevo partido de ${partido.tipoDeporte} el ${partido.fechaHora} en ${partido.ubicacion}`,
      partidoId: partido.id,
    }

    this.sendNotification(notification)
  }

  notifyPartidoArmado(usuarioId: number, partido: Partido): void {
    const notification: CreateNotificationRequest = {
      usuarioId,
      tipo: TipoNotificacion.PARTIDO_ARMADO,
      titulo: "¡Partido armado!",
      mensaje: `El partido de ${partido.tipoDeporte} ya tiene suficientes jugadores. ¡Prepárate para jugar!`,
      partidoId: partido.id,
    }

    this.sendNotification(notification)
  }

  notifyPartidoConfirmado(usuarioId: number, partido: Partido): void {
    const notification: CreateNotificationRequest = {
      usuarioId,
      tipo: TipoNotificacion.PARTIDO_CONFIRMADO,
      titulo: "Partido confirmado",
      mensaje: `El partido de ${partido.tipoDeporte} ha sido confirmado para el ${partido.fechaHora}`,
      partidoId: partido.id,
    }

    this.sendNotification(notification)
  }

  notifyPartidoEnJuego(usuarioId: number, partido: Partido): void {
    const notification: CreateNotificationRequest = {
      usuarioId,
      tipo: TipoNotificacion.PARTIDO_EN_JUEGO,
      titulo: "¡Partido en juego!",
      mensaje: `El partido de ${partido.tipoDeporte} ha comenzado. ¡Que disfrutes el juego!`,
      partidoId: partido.id,
    }

    this.sendNotification(notification)
  }

  notifyPartidoFinalizado(usuarioId: number, partido: Partido): void {
    const notification: CreateNotificationRequest = {
      usuarioId,
      tipo: TipoNotificacion.PARTIDO_FINALIZADO,
      titulo: "Partido finalizado",
      mensaje: `El partido de ${partido.tipoDeporte} ha terminado. ¡Esperamos que lo hayas disfrutado!`,
      partidoId: partido.id,
    }

    this.sendNotification(notification)
  }

  notifyPartidoCancelado(usuarioId: number, partido: Partido): void {
    const notification: CreateNotificationRequest = {
      usuarioId,
      tipo: TipoNotificacion.PARTIDO_CANCELADO,
      titulo: "Partido cancelado",
      mensaje: `Lamentamos informarte que el partido de ${partido.tipoDeporte} ha sido cancelado`,
      partidoId: partido.id,
    }

    this.sendNotification(notification)
  }

  private sendNotification(notification: CreateNotificationRequest): void {
    // Simular envío de notificación
    const newNotification: Notification = {
      id: Date.now(),
      ...notification,
      leida: false,
      fechaCreacion: new Date().toISOString(),
    }

    const currentNotifications = this.notificationsSubject.value
    const updatedNotifications = [newNotification, ...currentNotifications]
    this.notificationsSubject.next(updatedNotifications)
    this.updateUnreadCount()


    this.showPushNotification(newNotification)


    this.sendEmailNotification(newNotification)
  }

  private showPushNotification(notification: Notification): void {
    if ("Notification" in window && Notification.permission === "granted") {
      new Notification(notification.titulo, {
        body: notification.mensaje,
        icon: "/assets/icons/icon-192x192.png",
        badge: "/assets/icons/badge-72x72.png",
      })
    }
  }

  private sendEmailNotification(notification: Notification): void {

    console.log("📧 Email enviado:", {
      to: "usuario@email.com",
      subject: notification.titulo,
      body: notification.mensaje,
    })
  }


  requestNotificationPermission(): Promise<NotificationPermission> {
    if ("Notification" in window) {
      return Notification.requestPermission()
    }
    return Promise.resolve("denied")
  }


  markNotificationAsRead(notificationId: number): void {
    const notifications = this.notificationsSubject.value
    const updatedNotifications = notifications.map((n) =>
      n.id === notificationId ? { ...n, leida: true, fechaLectura: new Date().toISOString() } : n,
    )
    this.notificationsSubject.next(updatedNotifications)
    this.updateUnreadCount()
  }

  markAllNotificationsAsRead(): void {
    const notifications = this.notificationsSubject.value
    const updatedNotifications = notifications.map((n) => ({
      ...n,
      leida: true,
      fechaLectura: new Date().toISOString(),
    }))
    this.notificationsSubject.next(updatedNotifications)
    this.updateUnreadCount()
  }

  deleteNotificationLocal(notificationId: number): void {
    const notifications = this.notificationsSubject.value
    const updatedNotifications = notifications.filter((n) => n.id !== notificationId)
    this.notificationsSubject.next(updatedNotifications)
    this.updateUnreadCount()
  }

  private updateUnreadCount(): void {
    const notifications = this.notificationsSubject.value
    const unreadCount = notifications.filter((n) => !n.leida).length
    this.unreadCountSubject.next(unreadCount)
  }


  getLocalNotifications(): Notification[] {
    return this.notificationsSubject.value
  }

  simulateNewMatchNotification(): void {
    const mockPartido: Partido = {
      id: Date.now(),
      tipoDeporte: "Fútbol",
      cantidadJugadores: 22,
      duracion: 90,
      ubicacion: "Cancha Norte",
      fechaHora: "2024-01-20T19:00:00",
      estado: {} as any,
      jugadoresInscritos: 5,
    }

    this.notifyNuevoPartido(1, mockPartido)
  }

  simulateMatchReadyNotification(): void {
    const mockPartido: Partido = {
      id: Date.now(),
      tipoDeporte: "Básquet",
      cantidadJugadores: 10,
      duracion: 60,
      ubicacion: "Gimnasio Central",
      fechaHora: "2024-01-21T20:00:00",
      estado: {} as any,
      jugadoresInscritos: 10,
    }

    this.notifyPartidoArmado(1, mockPartido)
  }
}
