import { Component, OnInit } from "@angular/core"
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from "@angular/forms"
import { Usuario } from "../../models/usuario.model"
import { CommonModule } from "@angular/common"

@Component({
  selector: "app-register-form",
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule], 
  templateUrl: "./register-form.component.html",
  styleUrls: ["./register-form.component.css"],
})
export class RegisterFormComponent implements OnInit {
  registerForm!: FormGroup
  isSubmitting = false

  deportes = [
    "Fútbol",
    "Básquetbol",
    "Tenis",
    "Pádel",
    "Vóleibol",
    "Rugby",
    "Hockey",
    "Natación",
    "Atletismo",
    "Ciclismo",
  ]

  nivelesJuego = ["Principiante", "Intermedio", "Avanzado", "Profesional"]

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.initializeForm()
  }

  private initializeForm(): void {
    this.registerForm = this.formBuilder.group({
      username: ["", [Validators.required, Validators.minLength(3)]],
      email: ["", [Validators.required, Validators.email]],
      password: ["", [Validators.required, Validators.minLength(6)]],
      deporteFavorito: ["", Validators.required],
      nivelJuego: [""],
      ubicacion: this.formBuilder.group({
        nombreCalle: ["", Validators.required],
        numero: ["", Validators.required],
        ciudad: ["", Validators.required],
      }),
    })
  }

  get f() {
    return this.registerForm.controls
  }

  get ubicacionControls() {
    return (this.registerForm.get("ubicacion") as FormGroup).controls
  }

  getErrorMessage(controlName: string): string {
    const control = this.registerForm.get(controlName)

    if (control?.hasError("required")) {
      return `${this.getFieldLabel(controlName)} es requerido`
    }

    if (control?.hasError("email")) {
      return "Ingresa un email válido"
    }

    if (control?.hasError("minlength")) {
      const requiredLength = control.errors?.["minlength"].requiredLength
      return `Debe tener al menos ${requiredLength} caracteres`
    }

    return ""
  }

  getUbicacionErrorMessage(controlName: string): string {
    const control = this.registerForm.get(`ubicacion.${controlName}`)

    if (control?.hasError("required")) {
      return `${this.getFieldLabel(controlName)} es requerido`
    }

    return ""
  }

  private getFieldLabel(controlName: string): string {
    const labels: { [key: string]: string } = {
      username: "El nombre de usuario",
      email: "El email",
      password: "La contraseña",
      deporteFavorito: "El deporte favorito",
      nombreCalle: "El nombre de la calle",
      numero: "El número",
      ciudad: "La ciudad",
    }

    return labels[controlName] || "Este campo"
  }

  async onSubmit(): Promise<void> {
    if (this.registerForm.invalid) {
      this.markFormGroupTouched()
      return
    }

    this.isSubmitting = true

    try {
      const formData = this.registerForm.value


      if (!formData.nivelJuego) {
        formData.nivelJuego = null
      }

      const usuarioData: Usuario = formData

      console.log("Datos del usuario:", usuarioData)


      await new Promise((resolve) => setTimeout(resolve, 1000))

      alert("Usuario registrado exitosamente!")
      this.registerForm.reset()
    } catch (error) {
      console.error("Error al registrar usuario:", error)
      alert("Error al registrar usuario. Intenta nuevamente.")
    } finally {
      this.isSubmitting = false
    }
  }

  private markFormGroupTouched(): void {
    Object.keys(this.registerForm.controls).forEach((key) => {
      const control = this.registerForm.get(key)
      control?.markAsTouched()

      if (control instanceof FormGroup) {
        Object.keys(control.controls).forEach((nestedKey) => {
          control.get(nestedKey)?.markAsTouched()
        })
      }
    })
  }

  isFieldInvalid(controlName: string): boolean {
    const control = this.registerForm.get(controlName)
    return !!(control && control.invalid && control.touched)
  }

  isUbicacionFieldInvalid(controlName: string): boolean {
    const control = this.registerForm.get(`ubicacion.${controlName}`)
    return !!(control && control.invalid && control.touched)
  }
}