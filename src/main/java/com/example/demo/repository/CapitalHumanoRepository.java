package com.example.demo.repository;

import com.example.demo.model.CapitalHumano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CapitalHumanoRepository extends JpaRepository<CapitalHumano, Integer> {
    // 🔐 AGREGAR ESTOS MÉTODOS PARA LOGIN
    Optional<CapitalHumano> findByCorreoCapHum(String correoCapHum);
    Optional<CapitalHumano> findByCorreoCapHumAndContraseñaCapHum(String correoCapHum, String contraseñaCapHum);
}