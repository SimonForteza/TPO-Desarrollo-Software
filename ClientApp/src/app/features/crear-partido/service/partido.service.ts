import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environment/environment';
import { Partido, CreatePartidoRequest } from '../../../models/partido.model';

@Injectable({
  providedIn: 'root'
})
export class PartidoService {
  private apiUrl = `${environment.apiUrl}/partidos`;

  constructor(private http: HttpClient) { }


  createPartido(partido: CreatePartidoRequest): Observable<Partido> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post<Partido>(this.apiUrl, partido, { headers });
  }


  getAllPartidos(): Observable<Partido[]> {
    return this.http.get<Partido[]>(this.apiUrl);
  }


  getPartidoById(id: number): Observable<Partido> {
    return this.http.get<Partido>(`${this.apiUrl}/${id}`);
  }


  getDeportesDisponibles(): Observable<string[]> {
    return this.http.get<string[]>(`${environment.apiUrl}/deportes`);
  }


  joinPartido(partidoId: number, usuarioId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/${partidoId}/join`, { usuarioId });
  }


  cancelPartido(partidoId: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/${partidoId}/cancel`, {});
  }


  getPartidosByEstado(estado: string): Observable<Partido[]> {
    return this.http.get<Partido[]>(`${this.apiUrl}/estado/${estado}`);
  }


  getPartidosUsuario(usuarioId: number): Observable<Partido[]> {
    return this.http.get<Partido[]>(`${this.apiUrl}/usuario/${usuarioId}`);
  }
}
