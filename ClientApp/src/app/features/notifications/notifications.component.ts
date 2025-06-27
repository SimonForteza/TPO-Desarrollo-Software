import { Component, type OnInit, type OnDestroy, Input } from "@angular/core"
import { CommonModule } from "@angular/common"
import { Router } from "@angular/router"
import { Subscription } from "rxjs"
import { NotificationService } from "../notifications/service/notification.service"
import { Notification, TipoNotificacion } from "../../models/notification.model"


@Component({
  selector: "app-notifications",
  standalone: true,
  imports: [CommonModule],
  templateUrl: "./notifications.component.html",
  styleUrls: ["./notifications.component.css"],
})
export class NotificationsComponent implements OnInit, OnDestroy {
  @Input() showAsButton = false 
  
  notifications: Notification[] = []
  unreadCount = 0
  isLoading = false
  recentNotifications: Notification[] = [] 
  showDropdown = false 
  private subscriptions: Subscription[] = []

  constructor(
    private notificationService: NotificationService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.loadNotifications()
    this.subscribeToNotifications()
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe())
  }

  private loadNotifications(): void {
    this.isLoading = true


    setTimeout(() => {
      this.notifications = this.notificationService.getLocalNotifications()
      this.isLoading = false
    }, 500)
  }

  private subscribeToNotifications(): void {
    const notificationsSub = this.notificationService.notifications$.subscribe((notifications) => {
      this.notifications = notifications
      this.recentNotifications = notifications.slice(0, 3) // Para el dropdown
    })

    const unreadSub = this.notificationService.unreadCount$.subscribe((count) => {
      this.unreadCount = count
    })

    this.subscriptions.push(notificationsSub, unreadSub)
  }

  markAsRead(notification: Notification): void {
    if (!notification.leida && notification.id) {
      this.notificationService.markNotificationAsRead(notification.id)
    }
  }

  markAllAsRead(): void {
    this.notificationService.markAllNotificationsAsRead()
  }

  deleteNotification(notificationId: number): void {
    this.notificationService.deleteNotificationLocal(notificationId)
  }

  goToPartido(partidoId?: number): void {
    if (partidoId) {
      console.log(`Navegando al partido ${partidoId}`)
    }
  }

  goBack(): void {
    this.router.navigate(["/"])
  }


  toggleDropdown(): void {
    this.showDropdown = !this.showDropdown
  }

  goToNotificationsPage(): void {
    this.showDropdown = false
    this.router.navigate(['/notifications'])
  }

  getNotificationIcon(tipo: TipoNotificacion): string {
    switch (tipo) {
      case "NUEVO_PARTIDO":
        return "🆕"
      case "PARTIDO_ARMADO":
        return "✅"
      case "PARTIDO_CONFIRMADO":
        return "📅"
      case "PARTIDO_EN_JUEGO":
        return "⚡"
      case "PARTIDO_FINALIZADO":
        return "🏁"
      case "PARTIDO_CANCELADO":
        return "❌"
      case "JUGADOR_UNIDO":
        return "👥"
      case "RECORDATORIO":
        return "⏰"
      default:
        return "📢"
    }
  }

  getNotificationColor(tipo: TipoNotificacion): string {
    switch (tipo) {
      case "NUEVO_PARTIDO":
        return "#10b981"
      case "PARTIDO_ARMADO":
        return "#3b82f6"
      case "PARTIDO_CONFIRMADO":
        return "#8b5cf6"
      case "PARTIDO_EN_JUEGO":
        return "#f59e0b"
      case "PARTIDO_FINALIZADO":
        return "#6b7280"
      case "PARTIDO_CANCELADO":
        return "#ef4444"
      case "JUGADOR_UNIDO":
        return "#06b6d4"
      case "RECORDATORIO":
        return "#f97316"
      default:
        return "#6b7280"
    }
  }

  getTimeAgo(fechaCreacion: string): string {
    const now = new Date()
    const created = new Date(fechaCreacion)
    const diffMs = now.getTime() - created.getTime()
    const diffMins = Math.floor(diffMs / 60000)
    const diffHours = Math.floor(diffMins / 60)
    const diffDays = Math.floor(diffHours / 24)

    if (diffMins < 1) return "Ahora"
    if (diffMins < 60) return `Hace ${diffMins} min`
    if (diffHours < 24) return `Hace ${diffHours}h`
    if (diffDays < 7) return `Hace ${diffDays}d`
    return created.toLocaleDateString()
  }

  // Métodos para testing
  simulateNewMatch(): void {
    this.notificationService.simulateNewMatchNotification()
  }

  simulateMatchReady(): void {
    this.notificationService.simulateMatchReadyNotification()
  }
}