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
    private final TrabajadorRepository trabajadorRepository;

    public List<Ficha> getAll() {
        return fichaRepository.findAll();
    }

    public Ficha getById(Integer id) {
        return fichaRepository.findById(id).orElse(null);
    }

    public Ficha save(Ficha ficha) {
        if (ficha.getContratista() != null &&
                !contratistaRepository.existsById(ficha.getContratista().getIdContratista())) {
            throw new RuntimeException("Contratista no encontrado");
        }

        if (ficha.getProyecto() != null &&
                !proyectoRepository.existsById(ficha.getProyecto().getIdProyecto())) {
            throw new RuntimeException("Proyecto no encontrado");
        }

        return fichaRepository.save(ficha);
    }

    // ================================
    // 🔥 MÉTODO PARA CREAR FICHA CON TRABAJADORES
    // ================================
    public Ficha crearFichaConTrabajadores(Ficha ficha, List<Integer> trabajadoresIds) {

        // 1️⃣ Validaciones existentes
        if (!contratistaRepository.existsById(ficha.getContratista().getIdContratista())) {
            throw new RuntimeException("Contratista no encontrado");
        }

        if (!proyectoRepository.existsById(ficha.getProyecto().getIdProyecto())) {
            throw new RuntimeException("Proyecto no encontrado");
        }

        // 2️⃣ Guardar ficha
        Ficha fichaCreada = fichaRepository.save(ficha);

        // 3️⃣ Marcar trabajadores como OCUPADOS
        for (Integer idTrabajador : trabajadoresIds) {
            Trabajador trabajador = trabajadorRepository.findById(idTrabajador)
                    .orElseThrow(() ->
                            new RuntimeException("Trabajador no encontrado: " + idTrabajador)
                    );

            // 🔴 AQUÍ SE MARCA OCUPADO
            trabajador.setFicha(fichaCreada);
            trabajadorRepository.save(trabajador);
        }

        return fichaCreada;
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

        if (data.getFichaEstado() != null) {
            existing.setFichaEstado(data.getFichaEstado());
        }

        if (data.getFichaEspecialidad() != null) {
            existing.setFichaEspecialidad(data.getFichaEspecialidad());
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