package com.example.demo.repository;

import com.example.demo.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
    boolean existsByNombreProyecto(String nombreProyecto);
}