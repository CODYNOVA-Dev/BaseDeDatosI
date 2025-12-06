package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FichaService {

    private final FichaRepository fichaRepository;
    private final ContratistaRepository contratistaRepository;
    private final ProyectoRepository proyectoRepository;

    public List<Ficha> getAll() {
        return fichaRepository.findAll();
    }

    public Ficha getById(Integer id) {
        return fichaRepository.findById(id).orElse(null);
    }

    public Ficha save(Ficha ficha) {
        if (!contratistaRepository.existsById(ficha.getContratista().getIdContratista())) {
            throw new RuntimeException("Contratista no encontrado");
        }

        if (!proyectoRepository.existsById(ficha.getProyecto().getIdProyecto())) {
            throw new RuntimeException("Proyecto no encontrado");
        }

        return fichaRepository.save(ficha);
    }

    public void delete(Integer id) {
        fichaRepository.deleteById(id);
    }

    public Ficha update(Integer id, Ficha data) {
        Ficha existing = getById(id);
        if (existing == null) return null;

        if (data.getContratista() != null) {
            existing.setContratista(data.getContratista());
        }

        if (data.getProyecto() != null) {
            existing.setProyecto(data.getProyecto());
        }

        return fichaRepository.save(existing);
    }

    public List<Ficha> getByProyecto(Integer idProyecto) {
        return fichaRepository.findByProyectoIdProyecto(idProyecto);
    }

    public List<Ficha> getByContratista(Integer idContratista) {
        return fichaRepository.findByContratistaIdContratista(idContratista);
    }
}
