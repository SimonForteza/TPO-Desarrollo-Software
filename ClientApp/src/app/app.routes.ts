import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { RegisterFormComponent} from './features/create-user/register-form.component';
import { LoginComponent } from './features/login/login.component';
import { MiPerfilComponent } from './features/my-profile/my-profile.component';
import { CrearPartidoComponent } from './features/crear-partido/crear-partido.component';
import { BuscarPartidoComponent } from "./features/buscar-partido/buscar-partido.component"
import { BuscarPartidoPorHistorialComponent } from "./features/buscar-partido/buscar-partido-por-historial/buscar-partido-por-historial.component"
import { BuscarPartidoPorCercaniaComponent } from "./features/buscar-partido/buscar-partido-por-cercania/buscar-partido-por-cercania.component"
import { BuscarPartidoPorNivelComponent } from "./features/buscar-partido/buscar-partido-por-nivel/buscar-partido-por-nivel.component"
import { NotificationsComponent } from "./features/notifications/notifications.component"


export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'create-user', component: RegisterFormComponent },
  { path: 'login', component: LoginComponent },
  { path: 'mi-perfil', component: MiPerfilComponent },
  { path: 'crear-partido', component: CrearPartidoComponent }, 
  { path: "buscar-partido", component: BuscarPartidoComponent },
  { path: "buscar-partido-por-historial", component: BuscarPartidoPorHistorialComponent },
  { path: "buscar-partido-por-cercania", component: BuscarPartidoPorCercaniaComponent },
  { path: "buscar-partido-por-nivel", component: BuscarPartidoPorNivelComponent },
  { path: "notifications", component: NotificationsComponent },
  { path: 'configuracion', component: HomeComponent }, 
  { path: '**', redirectTo: '' }
];
