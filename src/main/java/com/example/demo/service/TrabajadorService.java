package com.example.demo.service;

import com.example.demo.model.Trabajador;
import com.example.demo.repository.TrabajadorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
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

    public void saveAll(List<Trabajador> trabajadores) {
        trabajadorRepository.saveAll(trabajadores);
    }

    public void delete(Integer id) {
        trabajadorRepository.deleteById(id);
    }

    public Trabajador update(Integer id, Trabajador datos) {
        Trabajador trabajadorExistente = getById(id);
        if (trabajadorExistente == null) {
            log.warn("Intento de actualizar trabajador no existente con ID: {}", id);
            return null;
        }

        if (datos.getNssTrabajador() != null) {
            trabajadorExistente.setNssTrabajador(datos.getNssTrabajador());
        }
        if (datos.getNombreTrabajador() != null) {
            trabajadorExistente.setNombreTrabajador(datos.getNombreTrabajador());
        }
        if (datos.getEspecialidadTrabajador() != null) {
            trabajadorExistente.setEspecialidadTrabajador(datos.getEspecialidadTrabajador());
        }
        if (datos.getEstadoTrabajador() != null) {
            trabajadorExistente.setEstadoTrabajador(datos.getEstadoTrabajador());
        }
        if (datos.getDescripcionTrabajador() != null) {
            trabajadorExistente.setDescripcionTrabajador(datos.getDescripcionTrabajador());
        }

        log.info("Actualizando trabajador ID: {}", id);
        return trabajadorRepository.save(trabajadorExistente);
    }

    public boolean existsByNss(String nssTrabajador) {
        return trabajadorRepository.existsByNssTrabajador(nssTrabajador);
    }

    public Trabajador getByNss(String nssTrabajador) {
        return trabajadorRepository.findByNssTrabajador(nssTrabajador).orElse(null);
    }

    // Métodos para filtros
    public List<Trabajador> getByEstado(String estado) {
        log.debug("Buscando trabajadores por estado: {}", estado);
        return trabajadorRepository.findByEstadoTrabajador(estado);
    }

    public List<Trabajador> getByEspecialidad(String especialidad) {
        log.debug("Buscando trabajadores por especialidad: {}", especialidad);
        return trabajadorRepository.findByEspecialidadTrabajador(especialidad);
    }

    public List<Trabajador> getByEstadoAndEspecialidad(String estado, String especialidad) {
        log.debug("Buscando trabajadores por estado: {} y especialidad: {}", estado, especialidad);
        return trabajadorRepository.findByEstadoTrabajadorAndEspecialidadTrabajador(estado, especialidad);
    }

    public List<Trabajador> getFiltrados(String estado, String especialidad) {
        log.debug("Aplicando filtros - Estado: {}, Especialidad: {}", estado, especialidad);

        if (estado != null && !estado.isEmpty() && especialidad != null && !especialidad.isEmpty()) {
            return getByEstadoAndEspecialidad(estado, especialidad);
        } else if (estado != null && !estado.isEmpty()) {
            return getByEstado(estado);
        } else if (especialidad != null && !especialidad.isEmpty()) {
            return getByEspecialidad(especialidad);
        } else {
            log.debug("Sin filtros, devolviendo todos los trabajadores");
            return getAll();
        }
    }

    // ================================
// DISPONIBILIDAD
// ================================

    public List<Trabajador> getDisponibles() {
        return trabajadorRepository.findByFichaIsNull();
    }

    public List<Trabajador> getDisponiblesFiltrados(String estado, String especialidad) {

        if (estado != null && !estado.isEmpty()
                && especialidad != null && !especialidad.isEmpty()) {

            return trabajadorRepository
                    .findByFichaIsNullAndEstadoTrabajadorAndEspecialidadTrabajador(
                            estado, especialidad
                    );

        } else if (estado != null && !estado.isEmpty()) {

            return trabajadorRepository
                    .findByFichaIsNullAndEstadoTrabajador(estado);

        } else if (especialidad != null && !especialidad.isEmpty()) {

            return trabajadorRepository
                    .findByFichaIsNullAndEspecialidadTrabajador(especialidad);

        } else {
            return trabajadorRepository.findByFichaIsNull();
        }
    }


}