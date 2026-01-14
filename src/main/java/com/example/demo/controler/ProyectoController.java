package com.example.demo.controler;

import com.example.demo.dto.ProyectoDto;
import com.example.demo.model.Proyecto;
import com.example.demo.service.ProyectoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/indecsa/api")
@RestController
@AllArgsConstructor
public class ProyectoController {

    private final ProyectoService proyectoService;

    @GetMapping("/proyectos")
    public ResponseEntity<List<ProyectoDto>> getAll() {
        List<Proyecto> lista = proyectoService.getAll();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                lista.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/proyectos/{id}")
    public ResponseEntity<ProyectoDto> getById(@PathVariable("id") Integer id) {
        Proyecto proyecto = proyectoService.getById(id);
        if (proyecto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDto(proyecto));
    }

    @PostMapping("/proyectos")
    public ResponseEntity<ProyectoDto> create(@RequestBody ProyectoDto dto) {
        // Verificar si ya existe un proyecto con el mismo nombre
        if (proyectoService.existsByNombre(dto.getNombreProyecto())) {
            return ResponseEntity.badRequest().build();
        }

        Proyecto proyecto = convertToEntity(dto);
        Proyecto saved = proyectoService.save(proyecto);

        return ResponseEntity.ok(convertToDto(saved));
    }

    @PutMapping("/proyectos/{id}")
    public ResponseEntity<ProyectoDto> update(@PathVariable Integer id, @RequestBody ProyectoDto dto) {
        Proyecto proyecto = convertToEntity(dto);
        Proyecto actualizado = proyectoService.update(id, proyecto);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(convertToDto(actualizado));
    }

    @DeleteMapping("/proyectos/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        proyectoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private ProyectoDto convertToDto(Proyecto proyecto) {
        return ProyectoDto.builder()
                .idProyecto(proyecto.getIdProyecto())
                .nombreProyecto(proyecto.getNombreProyecto())
                .tipoProyecto(proyecto.getTipoProyecto())
                .lugarProyecto(proyecto.getLugarProyecto())
                .build();
    }

    private Proyecto convertToEntity(ProyectoDto dto) {
        return Proyecto.builder()
                .nombreProyecto(dto.getNombreProyecto())
                .tipoProyecto(dto.getTipoProyecto())
                .lugarProyecto(dto.getLugarProyecto())
                .build();
    }
}