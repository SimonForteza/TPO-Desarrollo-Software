import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Deporte } from '../../models/deporte.model';
import { Ubicacion } from '../../models/ubicacion.model';
import { CreateUserRequest } from '../../models/user-request.model';
import { UserService } from './service/user.service';

@Component({
  selector: 'app-create-user',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {
  userForm: FormGroup;
  deportes: Deporte[] = [];
  ubicaciones: Ubicacion[] = [];
  isSubmitting = false;
  showSuccessMessage = false;
  errorMessage = '';

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private userService: UserService
  ) {
    this.userForm = this.createForm();
  }

  ngOnInit(): void {
    this.loadDeportes();
    this.loadUbicaciones();
  }

  private createForm(): FormGroup {
    return this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      ubicacionId: ['', [Validators.required]],
      deporteFavoritoId: [''],
      nivelJuego: ['']
    });
  }

  private loadDeportes(): void {
    this.deportes = [
      { id: 1, nombre: 'Fútbol' },
      { id: 2, nombre: 'Básquet' },
      { id: 3, nombre: 'Tenis' },
      { id: 4, nombre: 'Vóley' },
      { id: 5, nombre: 'Paddle' }
    ];
  }

  private loadUbicaciones(): void {
    this.ubicaciones = [
      { id: 1, nombre: 'Buenos Aires - Palermo' },
      { id: 2, nombre: 'Buenos Aires - Belgrano' },
      { id: 3, nombre: 'Buenos Aires - San Telmo' },
      { id: 4, nombre: 'Córdoba - Centro' },
      { id: 5, nombre: 'Rosario - Centro' }
    ];
  }

  onSubmit(): void {
    if (this.userForm.valid) {
      this.isSubmitting = true;
      this.errorMessage = '';
      
      const formValue = this.userForm.value;
      
      // Crear el objeto directamente sin DTO separado
      const createUserRequest: CreateUserRequest = {
        username: formValue.username,
        email: formValue.email,
        password: formValue.password,
        ubicacionId: parseInt(formValue.ubicacionId),
        // Solo incluir campos opcionales si tienen valor
        ...(formValue.deporteFavoritoId && { deporteFavoritoId: parseInt(formValue.deporteFavoritoId) }),
        ...(formValue.nivelJuego && { nivelJuego: formValue.nivelJuego })
      };

      this.userService.crearUsuario(createUserRequest).subscribe({
        next: (usuario) => {
          console.log('Usuario creado exitosamente:', usuario);
          this.isSubmitting = false;
          this.showSuccessMessage = true;
          
          setTimeout(() => {
            this.showSuccessMessage = false;
            this.router.navigate(['/']);
          }, 3000);
        },
        error: (error) => {
          console.error('Error al crear usuario:', error);
          this.isSubmitting = false;
          this.errorMessage = error.message;
        }
      });
    } else {
      Object.keys(this.userForm.controls).forEach(key => {
        this.userForm.get(key)?.markAsTouched();
      });
    }
  }

  resetForm(): void {
    this.userForm.reset();
    this.showSuccessMessage = false;
    this.errorMessage = '';
  }

  goBack(): void {
    this.router.navigate(['/']);
  }
}