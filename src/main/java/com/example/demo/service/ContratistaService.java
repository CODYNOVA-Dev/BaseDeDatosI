package com.example.demo.service;

import com.example.demo.model.Contratista;
import com.example.demo.repository.ContratistaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ContratistaService {

    private final ContratistaRepository contratistaRepository;

    public List<Contratista> getAll() {
        return contratistaRepository.findAll();
    }

    public Contratista getById(Integer id) {
        return contratistaRepository.findById(id).orElse(null);
    }

    public Contratista save(Contratista contratista) {
        return contratistaRepository.save(contratista);
    }

    public void delete(Integer id) {
        contratistaRepository.deleteById(id);
    }

    public Contratista update(Integer id, Contratista datos) {
        Contratista existente = getById(id);
        if (existente == null) return null;

        existente.setNombreContratista(datos.getNombreContratista());
        existente.setEstadoContratista(datos.getEstadoContratista());
        existente.setDescripcionContratista(datos.getDescripcionContratista());
        existente.setCalificacion(datos.getCalificacion());
        existente.setEspecialidad(datos.getEspecialidad());
        existente.setTelefono(datos.getTelefono());
        existente.setCorreo(datos.getCorreo());

        return contratistaRepository.save(existente);
    }

    public boolean existsByCorreo(String correo) {
        return contratistaRepository.existsByCorreo(correo);
    }

    public Contratista getByCorreo(String correo) {
        return contratistaRepository.findByCorreo(correo).orElse(null);
    }
}