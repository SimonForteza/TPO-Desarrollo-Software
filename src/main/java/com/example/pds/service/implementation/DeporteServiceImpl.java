package com.example.pds.service.implementation;

import com.example.pds.service.DeporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pds.model.Deporte;
import com.example.pds.repository.DeporteRepository;

@Service
public class DeporteServiceImpl implements DeporteService {
    
    private DeporteRepository deporteRepository;

    @Autowired
    public DeporteServiceImpl(DeporteRepository deporteRepository) {
        this.deporteRepository = deporteRepository;
    }

    // Methods
    public Deporte crearDeporte(Deporte deporte){
        return deporteRepository.save(deporte);
    }
}
