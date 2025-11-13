package com.example.demo.repository;

import com.example.demo.model.CapitalHumano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional; // ← ESTE IMPORT FALTABA

@Repository
public interface CapitalHumanoRepository extends JpaRepository<CapitalHumano, Integer> {

    // CORREGIDO: "Optional" no "Optiona1" y "Correo" no "Correg"
    Optional<CapitalHumano> findByCorreoCapHum(String correoCapHum);

    Optional<CapitalHumano> findByCorreoCapHumAndContraseñaCapHum(String correoCapHum, String contraseñaCapHum);
}