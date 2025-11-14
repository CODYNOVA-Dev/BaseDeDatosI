package com.example.demo.repository;

import com.example.demo.model.Contratista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ContratistaRepository extends JpaRepository<Contratista, Integer> {
    Optional<Contratista> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    Optional<Contratista> findByTelefono(String telefono);
}