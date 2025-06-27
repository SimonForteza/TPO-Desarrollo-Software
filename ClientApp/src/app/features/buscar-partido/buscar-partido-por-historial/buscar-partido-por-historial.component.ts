import { Component, type OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { Router } from "@angular/router"

@Component({
  selector: "app-buscar-partido-por-historial",
  standalone: true,
  imports: [CommonModule],
  templateUrl: "./buscar-partido-por-historial.component.html",
  styleUrls: ["./buscar-partido-por-historial.component.css"],
})
export class BuscarPartidoPorHistorialComponent implements OnInit {
  isLoading = false
  partidosRecomendados: any[] = []

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.loadPartidosRecomendados()
  }

  goBack(): void {
    this.router.navigate(["/buscar-partido"])
  }

  private loadPartidosRecomendados(): void {
    this.isLoading = true

    // Simular carga de datos
    setTimeout(() => {
      this.partidosRecomendados = [
        {
          id: 1,
          tipoDeporte: "Fútbol 5",
          fecha: "2024-01-15",
          hora: "19:00",
          ubicacion: "Cancha Norte",
          jugadoresInscritos: 8,
          cantidadJugadores: 10,
          organizador: "Carlos Mendez",
        },
        {
          id: 2,
          tipoDeporte: "Básquet",
          fecha: "2024-01-16",
          hora: "20:30",
          ubicacion: "Gimnasio Central",
          jugadoresInscritos: 6,
          cantidadJugadores: 10,
          organizador: "Ana García",
        },
      ]
      this.isLoading = false
    }, 1500)
  }

  joinPartido(partidoId: number): void {
    console.log(`Unirse al partido ${partidoId}`)
    alert(`Te has unido al partido ${partidoId}`)
  }
}
