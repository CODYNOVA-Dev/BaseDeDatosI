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

    // 🔹 Obtener todos
    public List<Proyecto> getAll() {
        return proyectoRepository.findAll();
    }

    // 🔹 Obtener por ID
    public Proyecto getById(Integer id) {
        return proyectoRepository.findById(id).orElse(null);
    }

    // 🔹 Guardar
    public Proyecto save(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    // 🔹 Eliminar
    public void delete(Integer id) {
        proyectoRepository.deleteById(id);
    }

    // 🔹 Actualizar
    public Proyecto update(Integer id, Proyecto datos) {
        Proyecto proyectoExistente = getById(id);
        if (proyectoExistente == null) {
            return null;
        }

        proyectoExistente.setNombreProyecto(datos.getNombreProyecto());
        proyectoExistente.setTipoProyecto(datos.getTipoProyecto());
        proyectoExistente.setLugarProyecto(datos.getLugarProyecto());
        proyectoExistente.setClienteProyecto(datos.getClienteProyecto());
        proyectoExistente.setPresupuesto(datos.getPresupuesto());

        return proyectoRepository.save(proyectoExistente);
    }

    // 🔹 Validar si existe por nombre
    public boolean existsByNombre(String nombreProyecto) {
        return proyectoRepository.existsByNombreProyecto(nombreProyecto);
    }
}
