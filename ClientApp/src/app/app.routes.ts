import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: '',
        loadComponent: () => 
            import ('./features/home/home.component').then(x => x.HomeComponent)
    },
    {
        path: 'create-user',
        loadComponent: () => 
            import ('./features/create-user/create-user.component').then(x => x.CreateUserComponent)
    }
];
