import { CommonModule } from "@angular/common"
import { Component } from "@angular/core"
import { Router } from "@angular/router"
import { NotificationsComponent } from "../notifications/notifications.component"

@Component({
  selector: "app-home",
  standalone: true,
  imports: [CommonModule, NotificationsComponent],
  templateUrl: "./home.component.html",
  styleUrl: "./home.component.css",
})
export class HomeComponent {
  constructor(private router: Router) {}

  navigateToLogin() {
    console.log("Navegando a login...") 
    this.router.navigate(["/login"])
  }

  navigateToCreateUser() {
    console.log("Navegando a crear usuario...") 
    this.router.navigate(["/create-user"])
  }

  navigateToCreateSport() {
    console.log("Navegando a crear deporte...") 
    this.router.navigate(["/"])
  }

  navigateToCreateMatch() {
    console.log("Navegando a crear partido...") 
    this.router.navigate(["/crear-partido"])
  }


  navigateToBuscarPartido() {
    console.log("Navegando a buscar partido...")
    this.router.navigate(["/buscar-partido"])
  }
}
