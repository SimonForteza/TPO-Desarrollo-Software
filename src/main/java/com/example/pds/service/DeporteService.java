package com.example.pds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pds.model.Deporte;
import com.example.pds.repository.DeporteRepository;

@Service
public class DeporteService {
    
    private DeporteRepository deporteRepository;

    @Autowired
    public DeporteService(DeporteRepository deporteRepository) {
        this.deporteRepository = deporteRepository;
    }

    public Deporte crearDeporte(Deporte deporte){
        return deporteRepository.save(deporte);
    }
}
