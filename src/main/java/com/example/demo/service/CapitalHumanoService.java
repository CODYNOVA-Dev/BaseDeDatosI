package com.example.demo.service;

import com.example.demo.model.CapitalHumano;
import com.example.demo.repository.CapitalHumanoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class CapitalHumanoService {

    private final CapitalHumanoRepository repo;

    public List<CapitalHumano> getAll() {
        return repo.findAll();
    }

    public CapitalHumano getById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public void save(CapitalHumano u) {
        repo.save(u);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public CapitalHumano update(Integer id, CapitalHumano datos) {
        CapitalHumano u = getById(id);
        if (u == null) return null;

        u.setCorreoCapHum(datos.getCorreoCapHum());
        u.setContraseñaCapHum(datos.getContraseñaCapHum());
        return repo.save(u);
    }

    public CapitalHumano login(String correo, String contraseña) {
        return repo.findByCorreoCapHumAndContraseñaCapHum(correo, contraseña)
                .orElse(null);
    }

    public boolean existsByCorreo(String correo) {
        return repo.findByCorreoCapHum(correo).isPresent();
    }
}