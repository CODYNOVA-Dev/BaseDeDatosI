package com.example.demo.controler;

import com.example.demo.dto.FichaDto;
import com.example.demo.model.*;
import com.example.demo.service.FichaService;
import com.example.demo.service.TrabajadorService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/indecsa/api")
@RestController
@AllArgsConstructor
public class FichaController {

    private final FichaService fichaService;
    private final TrabajadorService trabajadorService;

    // =====================================================
    // GET ALL
    // =====================================================
    @GetMapping("/fichas")
    public ResponseEntity<List<FichaDto>> getAll() {
        List<Ficha> fichas = fichaService.getAll();

        if (fichas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<FichaDto> dtos = fichas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // =====================================================
    // CREATE (POST)
    // =====================================================
    @PostMapping("/fichas")
    public ResponseEntity<FichaDto> create(@RequestBody FichaDto dto) {
        try {
            // 1️⃣ Convertir DTO a entidad
            Ficha ficha = convertToEntity(dto);

            // 2️⃣ Guardar ficha primero para obtener ID
            Ficha savedFicha = fichaService.save(ficha);

            // 3️⃣ Asignar trabajadores existentes si se proporcionan IDs
            if (dto.getTrabajadoresIds() != null && !dto.getTrabajadoresIds().isEmpty()) {
                List<Trabajador> trabajadores = dto.getTrabajadoresIds().stream()
                        .map(trabajadorService::getById)
                        .filter(t -> t != null)
                        .peek(t -> t.setFicha(savedFicha))
                        .collect(Collectors.toList());

                // Guardar los trabajadores actualizados
                trabajadorService.saveAll(trabajadores);

                // Actualizar la lista de la ficha
                savedFicha.setTrabajadores(trabajadores);
            }

            // 4️⃣ Devolver DTO
            return ResponseEntity.ok(convertToDto(savedFicha));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


    // =====================================================
    // GET BY ID
    // =====================================================
    @GetMapping("/fichas/{id}")
    public ResponseEntity<FichaDto> getById(@PathVariable Integer id) {
        Ficha ficha = fichaService.getById(id);
        if (ficha == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDto(ficha));
    }

    // =====================================================
    // UPDATE
    // =====================================================
    @PutMapping("/fichas/{id}")
    public ResponseEntity<FichaDto> update(@PathVariable Integer id, @RequestBody FichaDto dto) {
        Ficha entity = convertToEntity(dto);
        Ficha actualizado = fichaService.update(id, entity);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(convertToDto(actualizado));
    }

    // =====================================================
    // DELETE
    // =====================================================
    @DeleteMapping("/fichas/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        fichaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // =====================================================
    // CONVERTERS
    // =====================================================
    private FichaDto convertToDto(Ficha ficha) {
        return FichaDto.builder()
                .idFicha(ficha.getIdFicha())
                .idContratista(ficha.getContratista().getIdContratista())
                .idProyecto(ficha.getProyecto().getIdProyecto())
                .fichaEstado(ficha.getFichaEstado())
                .fichaEspecialidad(ficha.getFichaEspecialidad())
                .trabajadoresIds(
                        ficha.getTrabajadores() != null ?
                                ficha.getTrabajadores().stream().map(Trabajador::getIdTrabajador).toList()
                                : null
                )
                .build();
    }

    private Ficha convertToEntity(FichaDto dto) {
        Ficha ficha = new Ficha();

        Contratista contratista = new Contratista();
        contratista.setIdContratista(dto.getIdContratista());
        ficha.setContratista(contratista);

        Proyecto proyecto = new Proyecto();
        proyecto.setIdProyecto(dto.getIdProyecto());
        ficha.setProyecto(proyecto);

        ficha.setFichaEstado(dto.getFichaEstado());
        ficha.setFichaEspecialidad(dto.getFichaEspecialidad());

        return ficha;
    }
}
