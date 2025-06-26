import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../../../environment/environment';
import { CreateUserRequest } from '../../../models/user-request.model';
import { Usuario } from '../../../models/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly apiUrl = `${environment.apiUrl}/usuarios`;

  constructor(private http: HttpClient) {}

  crearUsuario(usuarioData: CreateUserRequest): Observable<Usuario> {
    return this.http.post<Usuario>(this.apiUrl, usuarioData)
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError = (error: HttpErrorResponse): Observable<never> => {
    let errorMessage = 'Ha ocurrido un error inesperado';
    
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      if (error.status === 400 && typeof error.error === 'string') {
        errorMessage = error.error;
      } else {
        switch (error.status) {
          case 404:
            errorMessage = 'Recurso no encontrado';
            break;
          case 500:
            errorMessage = 'Error interno del servidor';
            break;
          case 0:
            errorMessage = 'No se pudo conectar con el servidor';
            break;
          default:
            errorMessage = `Error ${error.status}: ${error.message}`;
        }
      }
    }

    console.error('Error en UserService:', error);
    return throwError(() => new Error(errorMessage));
  }
}