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

    public void delete(Integer id) {
        proyectoRepository.deleteById(id);
    }

    public Proyecto update(Integer id, Proyecto datos) {
        Proyecto proyectoExistente = getById(id);
        if (proyectoExistente == null) return null;

        proyectoExistente.setNombreProyecto(datos.getNombreProyecto());
        proyectoExistente.setTipoProyecto(datos.getTipoProyecto());
        proyectoExistente.setLugarProyecto(datos.getLugarProyecto());
        proyectoExistente.setFechaInicioProyecto(datos.getFechaInicioProyecto());
        proyectoExistente.setFechaFinProyecto(datos.getFechaFinProyecto());
        proyectoExistente.setClienteProyecto(datos.getClienteProyecto());

        return proyectoRepository.save(proyectoExistente);
    }

    public boolean existsByNombre(String nombreProyecto) {
        return proyectoRepository.existsByNombreProyecto(nombreProyecto);
    }
}