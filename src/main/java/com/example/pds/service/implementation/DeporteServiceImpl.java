package com.example.pds.service.implementation;

import com.example.pds.service.DeporteService;
import com.example.pds.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pds.model.entity.Deporte;
import com.example.pds.repository.DeporteRepository;
import java.util.List;
import java.text.Normalizer;

@Service
public class DeporteServiceImpl extends BaseService implements DeporteService {
    
    private DeporteRepository deporteRepository;

    @Autowired
    public DeporteServiceImpl(DeporteRepository deporteRepository) {
        this.deporteRepository = deporteRepository;
    }

    // Methods
    public Deporte crearDeporte(Deporte deporte){
        validateStringNotNullOrEmpty(deporte.getNombre(), "nombre del deporte");
        // Normalizar el nombre: quitar tildes y pasar a minúsculas
        String nombreNormalizado = Normalizer.normalize(deporte.getNombre(), Normalizer.Form.NFD)
            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
            .toLowerCase();
        // Chequear si ya existe (en Java)
        boolean existe = deporteRepository.findAll().stream()
            .map(d -> Normalizer.normalize(d.getNombre(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase())
            .anyMatch(n -> n.equals(nombreNormalizado));
        if (existe) {
            throw new RuntimeException("Ya existe un deporte con ese nombre.");
        }
        deporte.setNombre(nombreNormalizado);
        return deporteRepository.save(deporte);
    }

    @Override
    public List<Deporte> obtenerTodosLosDeportes() {
        return deporteRepository.findAll();
    }

    @Override
    public void eliminarDeportePorId(Long id) {
        deporteRepository.deleteById(id);
    }
}
