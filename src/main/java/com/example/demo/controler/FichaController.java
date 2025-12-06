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

    @PostMapping("/fichas")
    public ResponseEntity<FichaDto> create(@RequestBody FichaDto dto) {
        try {
            Ficha ficha = convertToEntity(dto);
            Ficha savedFicha = fichaService.save(ficha);

            if (dto.getTrabajadoresIds() != null && !dto.getTrabajadoresIds().isEmpty()) {
                List<Trabajador> trabajadores = dto.getTrabajadoresIds().stream()
                        .map(trabajadorService::getById)
                        .filter(t -> t != null)
                        .peek(t -> t.setFicha(savedFicha))
                        .collect(Collectors.toList());

                trabajadorService.saveAll(trabajadores);
                savedFicha.setTrabajadores(trabajadores);
            }

            return ResponseEntity.ok(convertToDto(savedFicha));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/fichas/{id}")
    public ResponseEntity<FichaDto> getById(@PathVariable Integer id) {
        Ficha ficha = fichaService.getById(id);
        if (ficha == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDto(ficha));
    }

    @PutMapping("/fichas/{id}")
    public ResponseEntity<FichaDto> update(@PathVariable Integer id, @RequestBody FichaDto dto) {
        Ficha entity = convertToEntity(dto);
        Ficha actualizado = fichaService.update(id, entity);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(convertToDto(actualizado));
    }

    @DeleteMapping("/fichas/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        fichaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // =====================================================
    // NUEVOS MÉTODOS PARA LA APP ANDROID
    // =====================================================

    @GetMapping("/fichas/filtros")
    public ResponseEntity<List<FichaDto>> getFichasFiltradas(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String especialidad) {

        List<Ficha> fichas = fichaService.getAll();

        if (estado != null && !estado.isEmpty()) {
            fichas = fichas.stream()
                    .filter(f -> estado.equalsIgnoreCase(f.getFichaEstado()))
                    .collect(Collectors.toList());
        }

        if (especialidad != null && !especialidad.isEmpty()) {
            fichas = fichas.stream()
                    .filter(f -> especialidad.equalsIgnoreCase(f.getFichaEspecialidad()))
                    .collect(Collectors.toList());
        }

        if (fichas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<FichaDto> dtos = fichas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/fichas/estado/{estado}")
    public ResponseEntity<List<FichaDto>> getFichasPorEstado(
            @PathVariable String estado) {

        List<Ficha> fichas = fichaService.getAll().stream()
                .filter(f -> estado.equalsIgnoreCase(f.getFichaEstado()))
                .collect(Collectors.toList());

        if (fichas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<FichaDto> dtos = fichas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/fichas/especialidad/{especialidad}")
    public ResponseEntity<List<FichaDto>> getFichasPorEspecialidad(
            @PathVariable String especialidad) {

        List<Ficha> fichas = fichaService.getAll().stream()
                .filter(f -> especialidad.equalsIgnoreCase(f.getFichaEspecialidad()))
                .collect(Collectors.toList());

        if (fichas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<FichaDto> dtos = fichas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

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