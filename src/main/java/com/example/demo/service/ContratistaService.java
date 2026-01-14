package com.example.demo.service;

import com.example.demo.dto.ContratistaDto;
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

    // ✅ UPDATE CORRECTO (desde DTO)
    public Contratista updateFromDto(Integer id, ContratistaDto dto) {
        Contratista existente = contratistaRepository.findById(id).orElse(null);
        if (existente == null) return null;

        existente.setNombreContratista(dto.getNombreContratista());
        existente.setDescripcionContratista(dto.getDescripcionContratista());
        existente.setEspecialidad(dto.getEspecialidad());
        existente.setTelefono(dto.getTelefono());
        existente.setCorreo(dto.getCorreo());

        existente.setEstadoContratista(dto.getEstadoContratista());

        // 🔥 PROTECCIÓN CLAVE
        if (dto.getCalificacion() != null) {
            existente.setCalificacion(dto.getCalificacion());
        }

        return contratistaRepository.save(existente);
    }


    public boolean existsByCorreo(String correo) {
        return contratistaRepository.existsByCorreo(correo);
    }

    public Contratista getByCorreo(String correo) {
        return contratistaRepository.findByCorreo(correo).orElse(null);
    }
}
