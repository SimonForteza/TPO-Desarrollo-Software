import { Component, type OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from "@angular/forms"
import { Router } from "@angular/router"

interface PartidoCercano {
  id: number
  tipoDeporte: string
  fecha: string
  hora: string
  ubicacion: string
  latitud: number
  longitud: number
  jugadoresInscritos: number
  cantidadJugadores: number
  organizador: string
  distancia: number 
}

interface UserLocation {
  latitud: number
  longitud: number
}

@Component({
  selector: "app-buscar-partido-por-cercania",
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: "./buscar-partido-por-cercania.component.html",
  styleUrls: ["./buscar-partido-por-cercania.component.css"],
})
export class BuscarPartidoPorCercaniaComponent implements OnInit {
  searchForm!: FormGroup
  isLoading = false
  isGettingLocation = false
  partidosCercanos: PartidoCercano[] = []
  userLocation: UserLocation | null = null
  errorMessage = ""

  deportesDisponibles = [
    "Fútbol",
    "Básquet",
    "Tenis",
    "Vóley",
    "Paddle",
    "Fútbol 5",
    "Handball",
    "Rugby",
    "Hockey",
    "Natación",
  ]

  constructor(
    private fb: FormBuilder,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.initForm()
  }

  private initForm(): void {
    this.searchForm = this.fb.group({
      deporte: ["", [Validators.required]],
      latitud: ["", [Validators.required, Validators.min(-90), Validators.max(90)]],
      longitud: ["", [Validators.required, Validators.min(-180), Validators.max(180)]],
      radioKm: [5, [Validators.required, Validators.min(1), Validators.max(50)]],
    })
  }

  goBack(): void {
    this.router.navigate(["/buscar-partido"])
  }

  getCurrentLocation(): void {
    this.isGettingLocation = true
    this.errorMessage = ""

    if (!navigator.geolocation) {
      this.errorMessage = "La geolocalización no está soportada en este navegador"
      this.isGettingLocation = false
      return
    }

    navigator.geolocation.getCurrentPosition(
      (position) => {
        const lat = position.coords.latitude
        const lng = position.coords.longitude

        this.userLocation = { latitud: lat, longitud: lng }

        this.searchForm.patchValue({
          latitud: lat.toFixed(6),
          longitud: lng.toFixed(6),
        })

        this.isGettingLocation = false
        console.log(`Ubicación obtenida: ${lat}, ${lng}`)
      },
      (error) => {
        this.isGettingLocation = false
        switch (error.code) {
          case error.PERMISSION_DENIED:
            this.errorMessage = "Permiso de ubicación denegado"
            break
          case error.POSITION_UNAVAILABLE:
            this.errorMessage = "Información de ubicación no disponible"
            break
          case error.TIMEOUT:
            this.errorMessage = "Tiempo de espera agotado para obtener ubicación"
            break
          default:
            this.errorMessage = "Error desconocido al obtener ubicación"
            break
        }
      },
      {
        enableHighAccuracy: true,
        timeout: 10000,
        maximumAge: 60000,
      },
    )
  }

  onSearch(): void {
    if (this.searchForm.valid) {
      this.isLoading = true
      this.errorMessage = ""

      const formValue = this.searchForm.value
      const deporte = formValue.deporte
      const userLat = Number.parseFloat(formValue.latitud)
      const userLng = Number.parseFloat(formValue.longitud)
      const radio = formValue.radioKm


      setTimeout(() => {
        this.partidosCercanos = this.generateMockPartidos(deporte, userLat, userLng, radio)
        this.isLoading = false
      }, 2000)
    } else {
      this.markFormGroupTouched()
    }
  }

  private generateMockPartidos(deporte: string, userLat: number, userLng: number, radio: number): PartidoCercano[] {

    const mockPartidos: PartidoCercano[] = [
      {
        id: 1,
        tipoDeporte: deporte,
        fecha: "2024-01-15",
        hora: "19:00",
        ubicacion: this.getUbicacionPorDeporte(deporte, "Norte"),
        latitud: userLat + 0.01,
        longitud: userLng + 0.01,
        jugadoresInscritos: this.getJugadoresInscritos(deporte),
        cantidadJugadores: this.getCantidadJugadores(deporte),
        organizador: "Carlos Mendez",
        distancia: 0,
      },
      {
        id: 2,
        tipoDeporte: deporte,
        fecha: "2024-01-16",
        hora: "20:30",
        ubicacion: this.getUbicacionPorDeporte(deporte, "Central"),
        latitud: userLat + 0.02,
        longitud: userLng - 0.01,
        jugadoresInscritos: this.getJugadoresInscritos(deporte) - 2,
        cantidadJugadores: this.getCantidadJugadores(deporte),
        organizador: "Ana García",
        distancia: 0,
      },
      {
        id: 3,
        tipoDeporte: deporte,
        fecha: "2024-01-17",
        hora: "18:00",
        ubicacion: this.getUbicacionPorDeporte(deporte, "Sur"),
        latitud: userLat - 0.015,
        longitud: userLng + 0.02,
        jugadoresInscritos: Math.floor(this.getCantidadJugadores(deporte) / 2),
        cantidadJugadores: this.getCantidadJugadores(deporte),
        organizador: "Luis Rodríguez",
        distancia: 0,
      },
      {
        id: 4,
        tipoDeporte: deporte,
        fecha: "2024-01-18",
        hora: "17:30",
        ubicacion: this.getUbicacionPorDeporte(deporte, "Oeste"),
        latitud: userLat + 0.005,
        longitud: userLng - 0.025,
        jugadoresInscritos: this.getJugadoresInscritos(deporte) + 1,
        cantidadJugadores: this.getCantidadJugadores(deporte),
        organizador: "María López",
        distancia: 0,
      },
    ]


    return mockPartidos
      .map((partido) => ({
        ...partido,
        distancia: this.calculateDistance(userLat, userLng, partido.latitud, partido.longitud),
      }))
      .filter((partido) => partido.distancia <= radio)
      .sort((a, b) => a.distancia - b.distancia)
  }

  private getUbicacionPorDeporte(deporte: string, zona: string): string {
    const ubicaciones: { [key: string]: string } = {
      Fútbol: `Cancha de Fútbol ${zona}`,
      "Fútbol 5": `Cancha de Fútbol 5 ${zona}`,
      Básquet: `Gimnasio ${zona}`,
      Tenis: `Club de Tenis ${zona}`,
      Vóley: `Cancha de Vóley ${zona}`,
      Paddle: `Club de Paddle ${zona}`,
      Handball: `Polideportivo ${zona}`,
      Rugby: `Campo de Rugby ${zona}`,
      Hockey: `Cancha de Hockey ${zona}`,
      Natación: `Piscina ${zona}`,
    }
    return ubicaciones[deporte] || `Instalación Deportiva ${zona}`
  }

  private getCantidadJugadores(deporte: string): number {
    const cantidades: { [key: string]: number } = {
      Fútbol: 22,
      "Fútbol 5": 10,
      Básquet: 10,
      Tenis: 4,
      Vóley: 12,
      Paddle: 4,
      Handball: 14,
      Rugby: 30,
      Hockey: 22,
      Natación: 8,
    }
    return cantidades[deporte] || 10
  }

  private getJugadoresInscritos(deporte: string): number {
    const total = this.getCantidadJugadores(deporte)
    return Math.floor(total * (0.5 + Math.random() * 0.4))
  }

  private calculateDistance(lat1: number, lng1: number, lat2: number, lng2: number): number {
    const R = 6371 
    const dLat = this.deg2rad(lat2 - lat1)
    const dLng = this.deg2rad(lng2 - lng1)
    const a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(this.deg2rad(lat1)) * Math.cos(this.deg2rad(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2)
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    const distance = R * c
    return Math.round(distance * 100) / 100
  }

  private deg2rad(deg: number): number {
    return deg * (Math.PI / 180)
  }

  private markFormGroupTouched(): void {
    Object.keys(this.searchForm.controls).forEach((key) => {
      const control = this.searchForm.get(key)
      control?.markAsTouched()
    })
  }

  joinPartido(partidoId: number): void {
    console.log(`Unirse al partido ${partidoId}`)
    alert(`Te has unido al partido ${partidoId}`)
  }

  openInMaps(latitud: number, longitud: number): void {
    const url = `https://www.google.com/maps?q=${latitud},${longitud}`
    window.open(url, "_blank")
  }

  getDeporteIcon(deporte: string): string {
    const iconos: { [key: string]: string } = {
      Fútbol: "⚽",
      "Fútbol 5": "⚽",
      Básquet: "🏀",
      Tenis: "🎾",
      Vóley: "🏐",
      Paddle: "🏓",
      Handball: "🤾",
      Rugby: "🏉",
      Hockey: "🏑",
      Natación: "🏊",
    }
    return iconos[deporte] || "🏃"
  }
}
