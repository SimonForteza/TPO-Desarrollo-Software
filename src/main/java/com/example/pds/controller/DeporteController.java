package com.example.pds.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.pds.model.entity.Deporte;
import com.example.pds.service.implementation.DeporteServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/deportes")
public class DeporteController {

    private DeporteServiceImpl deporteService;

    @Autowired
    public DeporteController(DeporteServiceImpl deporteService) {
        this.deporteService = deporteService;
    }

    //endpoints
    @PostMapping
    public ResponseEntity<Deporte> crearDeporte(@RequestBody Deporte deporte){
        return ResponseEntity.ok(deporteService.crearDeporte(deporte));
    }

    @GetMapping
    public ResponseEntity<List<Deporte>> obtenerTodosLosDeportes(){
        return ResponseEntity.ok(deporteService.obtenerTodosLosDeportes());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDeporte(@PathVariable Long id) {
        deporteService.eliminarDeportePorId(id);
        return ResponseEntity.noContent().build();
    }
}
