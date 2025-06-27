import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { UserService } from '../create-user/service/user.service';

@Component({
  selector: 'app-my-profile',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MiPerfilComponent implements OnInit {
  currentUser: any = null;
  profileForm!: FormGroup;
  isEditMode = false;
  isLoading = false;

  // Datos de ejemplo - reemplazar con datos reales del backend
  recentMatches = [
    {
      nombre: 'Fútbol 5 - Liga Amateur',
      deporte: 'Fútbol',
      fecha: '15 Dic 2024',
      ubicacion: 'Cancha Norte',
      resultado: 'ganado'
    },
    {
      nombre: 'Torneo de Tenis',
      deporte: 'Tenis',
      fecha: '10 Dic 2024',
      ubicacion: 'Club Central',
      resultado: 'perdido'
    },
    {
      nombre: 'Básquet 3x3',
      deporte: 'Básquet',
      fecha: '5 Dic 2024',
      ubicacion: 'Polideportivo',
      resultado: 'empate'
    }
  ];

  favoriteSports = [
    {
      nombre: 'Fútbol',
      partidosJugados: 8
    },
    {
      nombre: 'Tenis',
      partidosJugados: 3
    },
    {
      nombre: 'Básquet',
      partidosJugados: 1
    }
  ];

  constructor(
    private fb: FormBuilder,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.loadCurrentUser();
    this.initForm();
  }

  private loadCurrentUser(): void {
    this.currentUser = this.userService.getCurrentUser();
  }

  private initForm(): void {
    this.profileForm = this.fb.group({
      nombre: [this.currentUser?.nombre || '', [Validators.required]],
      apellido: [this.currentUser?.apellido || '', [Validators.required]],
      email: [this.currentUser?.email || '', [Validators.required, Validators.email]],
      telefono: [this.currentUser?.telefono || '']
    });
  }

  toggleEditMode(): void {
    this.isEditMode = !this.isEditMode;
    if (this.isEditMode) {
      this.initForm(); // Reinicializar el formulario con los datos actuales
    }
  }

  cancelEdit(): void {
    this.isEditMode = false;
    this.initForm(); // Restaurar los valores originales
  }

  onSaveProfile(): void {
    if (this.profileForm.valid) {
      this.isLoading = true;
      
      const updatedProfile = {
        ...this.currentUser,
        ...this.profileForm.value
      };

      // Aquí harías la llamada al backend para actualizar el perfil
      // Por ahora simulamos la actualización
      setTimeout(() => {
        this.currentUser = updatedProfile;
        localStorage.setItem('currentUser', JSON.stringify(updatedProfile));
        this.isLoading = false;
        this.isEditMode = false;
      }, 1000);
    }
  }
}