package com.example.demo.service;

import com.example.demo.model.Trabajador;
import com.example.demo.repository.TrabajadorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;

    public List<Trabajador> getAll() {
        return trabajadorRepository.findAll();
    }

    public Trabajador getById(Integer id) {
        return trabajadorRepository.findById(id).orElse(null);
    }

    public Trabajador save(Trabajador trabajador) {
        return trabajadorRepository.save(trabajador);
    }

    public void delete(Integer id) {
        trabajadorRepository.deleteById(id);
    }

    public Trabajador update(Integer id, Trabajador datos) {
        Trabajador trabajadorExistente = getById(id);
        if (trabajadorExistente == null) return null;

        trabajadorExistente.setNssTrabajador(datos.getNssTrabajador());
        trabajadorExistente.setNombreTrabajador(datos.getNombreTrabajador());
        trabajadorExistente.setEspecialidadTrabajador(datos.getEspecialidadTrabajador());
        trabajadorExistente.setCategoriaTrabajador(datos.getCategoriaTrabajador());

        return trabajadorRepository.save(trabajadorExistente);
    }

    public boolean existsByNss(String nssTrabajador) {
        return trabajadorRepository.existsByNssTrabajador(nssTrabajador);
    }

    public Trabajador getByNss(String nssTrabajador) {
        return trabajadorRepository.findByNssTrabajador(nssTrabajador).orElse(null);
    }
}