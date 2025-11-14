package com.example.demo.controler;

import com.example.demo.dto.ContratistaDto;
import com.example.demo.model.Contratista;
import com.example.demo.service.ContratistaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/indecsa/api")
@RestController
@AllArgsConstructor
public class ContratistaController {

    private final ContratistaService contratistaService;

    @GetMapping("/contratistas")
    public ResponseEntity<List<ContratistaDto>> getAll() {
        List<Contratista> lista = contratistaService.getAll();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                lista.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/contratistas/{id}")
    public ResponseEntity<ContratistaDto> getById(@PathVariable("id") Integer id) {
        Contratista contratista = contratistaService.getById(id);
        if (contratista == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDto(contratista));
    }

    @PostMapping("/contratistas")
    public ResponseEntity<ContratistaDto> create(@RequestBody ContratistaDto dto) {
        // Verificar si ya existe un contratista con el mismo correo
        if (contratistaService.existsByCorreo(dto.getCorreo())) {
            return ResponseEntity.badRequest().build();
        }

        Contratista contratista = convertToEntity(dto);
        Contratista saved = contratistaService.save(contratista);

        return ResponseEntity.ok(convertToDto(saved));
    }

    @PutMapping("/contratistas/{id}")
    public ResponseEntity<ContratistaDto> update(@PathVariable Integer id, @RequestBody ContratistaDto dto) {
        Contratista contratista = convertToEntity(dto);
        Contratista actualizado = contratistaService.update(id, contratista);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(convertToDto(actualizado));
    }

    @DeleteMapping("/contratistas/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        contratistaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private ContratistaDto convertToDto(Contratista contratista) {
        return ContratistaDto.builder()
                .idContratista(contratista.getIdContratista())
                .nombreContratista(contratista.getNombreContratista())
                .estadoContratista(contratista.getEstadoContratista())
                .descripcionContratista(contratista.getDescripcionContratista())
                .calificacion(contratista.getCalificacion())
                .especialidad(contratista.getEspecialidad())
                .telefono(contratista.getTelefono())
                .correo(contratista.getCorreo())
                .build();
    }

    private Contratista convertToEntity(ContratistaDto dto) {
        return Contratista.builder()
                .nombreContratista(dto.getNombreContratista())
                .estadoContratista(dto.getEstadoContratista())
                .descripcionContratista(dto.getDescripcionContratista())
                .calificacion(dto.getCalificacion())
                .especialidad(dto.getEspecialidad())
                .telefono(dto.getTelefono())
                .correo(dto.getCorreo())
                .build();
    }
}