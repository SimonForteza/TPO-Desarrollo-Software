package com.example.pds.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ubicaciones")
public class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreCalle;
    private String numero;
    private String ciudad;
    private double longitud;
    private double latitud;

    public Ubicacion() {}

    public Ubicacion(String nombreCalle, String numero, String ciudad, double longitud, double latitud) {
        this.nombreCalle = nombreCalle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCalle() {
        return nombreCalle;
    }

    public void setNombreCalle(String nombreCalle) {
        this.nombreCalle = nombreCalle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
}
