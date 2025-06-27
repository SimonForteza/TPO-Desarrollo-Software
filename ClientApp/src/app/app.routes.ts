import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { RegisterFormComponent} from './features/create-user/register-form.component';
import { LoginComponent } from './features/login/login.component';
import { MiPerfilComponent } from './features/my-profile/my-profile.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'create-user', component: RegisterFormComponent },
  { path: 'login', component: LoginComponent },
  { path: 'mi-perfil', component: MiPerfilComponent },
  { path: 'crear-partido', component: HomeComponent }, // Temporal - crearemos el componente después
  { path: 'buscar-partido', component: HomeComponent }, // Temporal - crearemos el componente después
  { path: 'configuracion', component: HomeComponent }, // Temporal - crearemos el componente después
  { path: '**', redirectTo: '' }
];
