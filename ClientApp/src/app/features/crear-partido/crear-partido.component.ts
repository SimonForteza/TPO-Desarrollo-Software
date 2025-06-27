import { Component, type OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormBuilder, type FormGroup, Validators, ReactiveFormsModule } from "@angular/forms"
import { Router } from "@angular/router"
import { PartidoService } from "../crear-partido/service/partido.service"
import { UserService } from "../create-user/service/user.service"
import { CreatePartidoRequest } from "../../models/partido.model"

@Component({
  selector: "app-crear-partido",
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: "./crear-partido.component.html",
  styleUrls: ["./crear-partido.component.css"],
})
export class CrearPartidoComponent implements OnInit {
  partidoForm!: FormGroup
  isLoading = false
  errorMessage = ""
  successMessage = ""
  minDate = ""

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
    private partidoService: PartidoService,
    private userService: UserService,
  ) {}

  ngOnInit(): void {
    this.initForm()
    this.setMinDate()
    this.loadDeportes()
  }

  private initForm(): void {
    this.partidoForm = this.fb.group({
      tipoDeporte: ["", [Validators.required]],
      cantidadJugadores: ["", [Validators.required, Validators.min(2), Validators.max(50)]],
      duracion: ["", [Validators.required]],
      ubicacion: ["", [Validators.required]],
      fecha: ["", [Validators.required]],
      hora: ["", [Validators.required]],
      descripcion: [""],
    })
  }

  private setMinDate(): void {
    const today = new Date()
    this.minDate = today.toISOString().split("T")[0]
  }

  private loadDeportes(): void {
    // Aquí podrías cargar los deportes desde el backend
    // this.partidoService.getDeportesDisponibles().subscribe({
    //   next: (deportes) => {
    //     this.deportesDisponibles = deportes;
    //   },
    //   error: (error) => {
    //     console.error('Error cargando deportes:', error);
    //   }
    // });
  }

  onSubmit(): void {
    if (this.partidoForm.valid) {
      this.isLoading = true
      this.errorMessage = ""
      this.successMessage = ""

      const formValue = this.partidoForm.value

    
      const fechaHora = `${formValue.fecha}T${formValue.hora}:00`

      const partidoData: CreatePartidoRequest = {
        tipoDeporte: formValue.tipoDeporte,
        cantidadJugadores: Number.parseInt(formValue.cantidadJugadores),
        duracion: Number.parseInt(formValue.duracion),
        ubicacion: formValue.ubicacion,
        fechaHora: fechaHora,
        descripcion: formValue.descripcion || undefined,
      }

      this.partidoService.createPartido(partidoData).subscribe({
        next: (response) => {
          this.isLoading = false
          this.successMessage = "¡Partido creado exitosamente! Redirigiendo..."


          setTimeout(() => {
            this.router.navigate(["/"])
          }, 2000)
        },
        error: (error) => {
          this.isLoading = false
          console.error("Error creando partido:", error)

          if (error.status === 400) {
            this.errorMessage = "Datos inválidos. Verifica la información ingresada."
          } else if (error.status === 401) {
            this.errorMessage = "Debes iniciar sesión para crear un partido."
          } else {
            this.errorMessage = "Error al crear el partido. Intenta nuevamente."
          }
        },
      })
    } else {
      this.markFormGroupTouched()
    }
  }

  private markFormGroupTouched(): void {
    Object.keys(this.partidoForm.controls).forEach((key) => {
      const control = this.partidoForm.get(key)
      control?.markAsTouched()
    })
  }

  goBack(): void {
    this.router.navigate(["/"])
  }
}
