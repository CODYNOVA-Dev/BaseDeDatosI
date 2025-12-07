package com.example.demo.repository;

import com.example.demo.model.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Integer> {
    Optional<Trabajador> findByNssTrabajador(String nssTrabajador);
    boolean existsByNssTrabajador(String nssTrabajador);

    List<Trabajador> findByEstadoTrabajador(String estado);
    List<Trabajador> findByEspecialidadTrabajador(String especialidad);
    List<Trabajador> findByEstadoTrabajadorAndEspecialidadTrabajador(String estado, String especialidad);
}