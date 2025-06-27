import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"
import { Router } from "@angular/router"

@Component({
  selector: "app-buscar-partido",
  standalone: true,
  imports: [CommonModule],
  templateUrl: "./buscar-partido.component.html",
  styleUrls: ["./buscar-partido.component.css"],
})
export class BuscarPartidoComponent {
  constructor(private router: Router) {}

  goBack(): void {
    this.router.navigate(["/"])
  }

  navigateToBuscarPorHistorial(): void {
    console.log("Navegando a buscar por historial...")
    this.router.navigate(["/buscar-partido-por-historial"])
  }

  navigateToBuscarPorCercania(): void {
    console.log("Navegando a buscar por cercanía...")
    this.router.navigate(["/buscar-partido-por-cercania"])
  }

  navigateToBuscarPorNivel(): void {
    console.log("Navegando a buscar por nivel...")
    this.router.navigate(["/buscar-partido-por-nivel"])
  }
}
