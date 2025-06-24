package com.example.pds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.pds.model.Deporte;
import com.example.pds.service.DeporteService;

@RestController
@RequestMapping("/deportes")
public class DeporteController {

    private DeporteService deporteService;

    @Autowired
    public DeporteController(DeporteService deporteService) {
        this.deporteService = deporteService;
    }

    //endpoints
    @PostMapping
    public ResponseEntity<Deporte> createDeporte(@RequestBody Deporte deporte){
        return ResponseEntity.ok(deporteService.crearDeporte(deporte));
    }
}
