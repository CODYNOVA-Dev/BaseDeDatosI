package com.example.demo.repository;

import com.example.demo.model.Ficha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FichaRepository extends JpaRepository<Ficha, Integer> {

    List<Ficha> findByProyectoIdProyecto(Integer idProyecto);

    List<Ficha> findByContratistaIdContratista(Integer idContratista);
}
