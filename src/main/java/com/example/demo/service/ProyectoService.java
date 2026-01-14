package com.example.demo.service;

import com.example.demo.model.Proyecto;
import com.example.demo.repository.ProyectoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;

    public List<Proyecto> getAll() {
        return proyectoRepository.findAll();
    }

    public Proyecto getById(Integer id) {
        return proyectoRepository.findById(id).orElse(null);
    }

    public Proyecto save(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public Proyecto update(Integer id, Proyecto proyecto) {
        Proyecto existing = getById(id);
        if (existing == null) return null;

        existing.setNombreProyecto(proyecto.getNombreProyecto());
        existing.setTipoProyecto(proyecto.getTipoProyecto());
        existing.setLugarProyecto(proyecto.getLugarProyecto());

        return proyectoRepository.save(existing);
    }

    public void delete(Integer id) {
        proyectoRepository.deleteById(id);
    }

    public boolean existsByNombre(String nombre) {
        return proyectoRepository.existsByNombreProyecto(nombre);
    }
}