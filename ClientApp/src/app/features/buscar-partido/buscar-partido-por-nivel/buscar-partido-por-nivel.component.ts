import { Component, type OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from "@angular/forms"
import { Router } from "@angular/router"
import { NivelJugador } from "../../../models/partido.model"

interface PartidoPorNivel {
  id: number
  tipoDeporte: string
  fecha: string
  hora: string
  ubicacion: string
  jugadoresInscritos: number
  cantidadJugadores: number
  organizador: string
  nivelMinimo: NivelJugador
  nivelMaximo: NivelJugador
  compatibilidad: number
}

interface NivelDeporte {
  deporte: string
  nivel: NivelJugador
}

@Component({
  selector: "app-buscar-partido-por-nivel",
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: "./buscar-partido-por-nivel.component.html",
  styleUrls: ["./buscar-partido-por-nivel.component.css"],
})


export class BuscarPartidoPorNivelComponent implements OnInit {
  searchForm!: FormGroup
  isLoading = false
  partidosPorNivel: PartidoPorNivel[] = []
  errorMessage = ""

  // Simulamos los niveles del usuario
  userNiveles: NivelDeporte[] = [
    { deporte: "Fútbol", nivel: NivelJugador.INTERMEDIO },
    { deporte: "Básquet", nivel: NivelJugador.AVANZADO },
    { deporte: "Tenis", nivel: NivelJugador.PRINCIPIANTE },
  ]

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

  nivelesDisponibles = Object.values(NivelJugador)

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
      miNivel: ["", [Validators.required]],
      buscarNivelSimilar: [true], // Si buscar niveles similares o exactos
    })
  }

  goBack(): void {
    this.router.navigate(["/buscar-partido"])
  }

  onDeporteChange(): void {
    const deporteSeleccionado = this.searchForm.get("deporte")?.value


    const nivelUsuario = this.userNiveles.find((n) => n.deporte === deporteSeleccionado)
    if (nivelUsuario) {
      this.searchForm.patchValue({
        miNivel: nivelUsuario.nivel,
      })
    }
  }

  onSearch(): void {
    if (this.searchForm.valid) {
      this.isLoading = true
      this.errorMessage = ""

      const formValue = this.searchForm.value
      const deporte = formValue.deporte
      const miNivel = formValue.miNivel
      const buscarSimilar = formValue.buscarNivelSimilar

      // Simular búsqueda de partidos por nivel
      setTimeout(() => {
        this.partidosPorNivel = this.generateMockPartidosPorNivel(deporte, miNivel, buscarSimilar)
        this.isLoading = false
      }, 2000)
    } else {
      this.markFormGroupTouched()
    }
  }

  private generateMockPartidosPorNivel(
    deporte: string,
    miNivel: NivelJugador,
    buscarSimilar: boolean,
  ): PartidoPorNivel[] {
    const mockPartidos: PartidoPorNivel[] = [
      {
        id: 1,
        tipoDeporte: deporte,
        fecha: "2024-01-15",
        hora: "19:00",
        ubicacion: "Cancha Norte",
        jugadoresInscritos: 8,
        cantidadJugadores: 10,
        organizador: "Carlos Mendez",
        nivelMinimo: NivelJugador.PRINCIPIANTE,
        nivelMaximo: NivelJugador.INTERMEDIO,
        compatibilidad: 0,
      },
      {
        id: 2,
        tipoDeporte: deporte,
        fecha: "2024-01-16",
        hora: "20:30",
        ubicacion: "Gimnasio Central",
        jugadoresInscritos: 6,
        cantidadJugadores: 10,
        organizador: "Ana García",
        nivelMinimo: NivelJugador.INTERMEDIO,
        nivelMaximo: NivelJugador.AVANZADO,
        compatibilidad: 0,
      },
      {
        id: 3,
        tipoDeporte: deporte,
        fecha: "2024-01-17",
        hora: "18:00",
        ubicacion: "Club Deportivo",
        jugadoresInscritos: 2,
        cantidadJugadores: 4,
        organizador: "Luis Rodríguez",
        nivelMinimo: NivelJugador.AVANZADO,
        nivelMaximo: NivelJugador.EXPERTO,
        compatibilidad: 0,
      },
      {
        id: 4,
        tipoDeporte: deporte,
        fecha: "2024-01-18",
        hora: "17:30",
        ubicacion: "Polideportivo",
        jugadoresInscritos: 4,
        cantidadJugadores: 8,
        organizador: "María López",
        nivelMinimo: NivelJugador.PRINCIPIANTE,
        nivelMaximo: NivelJugador.PRINCIPIANTE,
        compatibilidad: 0,
      },
    ]


    return mockPartidos
      .map((partido) => ({
        ...partido,
        compatibilidad: this.calculateCompatibility(miNivel, partido.nivelMinimo, partido.nivelMaximo),
      }))
      .filter((partido) => {
        if (buscarSimilar) {
          return this.isUserLevelCompatible(miNivel, partido.nivelMinimo, partido.nivelMaximo)
        } else {

          return partido.nivelMinimo === miNivel || partido.nivelMaximo === miNivel
        }
      })
      .sort((a, b) => b.compatibilidad - a.compatibilidad)
  }

  private calculateCompatibility(userLevel: NivelJugador, minLevel: NivelJugador, maxLevel: NivelJugador): number {
    const levelValues = {
      [NivelJugador.PRINCIPIANTE]: 1,
      [NivelJugador.INTERMEDIO]: 2,
      [NivelJugador.AVANZADO]: 3,
      [NivelJugador.EXPERTO]: 4,
    }

    const userValue = levelValues[userLevel]
    const minValue = levelValues[minLevel]
    const maxValue = levelValues[maxLevel]

    if (userValue >= minValue && userValue <= maxValue) {

      const rangeCenter = (minValue + maxValue) / 2
      const distance = Math.abs(userValue - rangeCenter)
      const maxDistance = (maxValue - minValue) / 2 || 0.5
      return Math.round((1 - distance / maxDistance) * 100)
    } else {
      const distanceToRange = Math.min(Math.abs(userValue - minValue), Math.abs(userValue - maxValue))
      return Math.max(0, Math.round((1 - distanceToRange / 4) * 100))
    }
  }

  private isUserLevelCompatible(userLevel: NivelJugador, minLevel: NivelJugador, maxLevel: NivelJugador): boolean {
    const levelValues = {
      [NivelJugador.PRINCIPIANTE]: 1,
      [NivelJugador.INTERMEDIO]: 2,
      [NivelJugador.AVANZADO]: 3,
      [NivelJugador.EXPERTO]: 4,
    }

    const userValue = levelValues[userLevel]
    const minValue = levelValues[minLevel]
    const maxValue = levelValues[maxLevel]

    // Permitir cierta flexibilidad: el usuario puede estar 1 nivel por debajo o por encima
    return userValue >= minValue - 1 && userValue <= maxValue + 1
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

  getNivelColor(nivel: NivelJugador): string {
    switch (nivel) {
      case NivelJugador.PRINCIPIANTE:
        return "#10b981" 
      case NivelJugador.INTERMEDIO:
        return "#f59e0b" 
      case NivelJugador.AVANZADO:
        return "#ef4444" 
      case NivelJugador.EXPERTO:
        return "#8b5cf6" 
      default:
        return "#6b7280" 
    }
  }

  getCompatibilityColor(compatibilidad: number): string {
    if (compatibilidad >= 80) return "#10b981" 
    if (compatibilidad >= 60) return "#f59e0b" 
    if (compatibilidad >= 40) return "#ef4444" 
    return "#6b7280" 
  }
}
