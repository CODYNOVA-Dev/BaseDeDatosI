package com.example.demo.repository;

import com.example.demo.model.Ficha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FichaRepository extends JpaRepository<Ficha, Integer> {

    // Buscar fichas por proyecto
    List<Ficha> findByProyectoIdProyecto(Integer idProyecto);

    // Buscar fichas por contratista
    List<Ficha> findByContratistaIdContratista(Integer idContratista);

    // Buscar fichas por admin
    List<Ficha> findByAdminIdAdmin(Integer idAdmin);

    // Buscar fichas por capital humano
    List<Ficha> findByCapitalHumanoIdCapHum(Integer idCapHum);
}