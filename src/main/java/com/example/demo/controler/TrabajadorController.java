package com.example.demo.controler;

import com.example.demo.dto.TrabajadorDto;
import com.example.demo.model.Trabajador;
import com.example.demo.service.TrabajadorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/indecsa/api")
@RestController
@AllArgsConstructor
public class TrabajadorController {

    private final TrabajadorService trabajadorService;

    // 🔹 GET: Obtener todos los trabajadores
    @GetMapping("/trabajadores")
    public ResponseEntity<List<TrabajadorDto>> getAll() {
        List<Trabajador> lista = trabajadorService.getAll();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                lista.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList())
        );
    }

    // 🔹 GET: Obtener trabajador por ID
    @GetMapping("/trabajadores/{id}")
    public ResponseEntity<TrabajadorDto> getById(@PathVariable Integer id) {
        Trabajador trabajador = trabajadorService.getById(id);
        if (trabajador == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDto(trabajador));
    }

    // 🔹 GET: Obtener trabajador por NSS
    @GetMapping("/trabajadores/nss/{nss}")
    public ResponseEntity<TrabajadorDto> getByNss(@PathVariable String nss) {
        Trabajador trabajador = trabajadorService.getByNss(nss);
        if (trabajador == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDto(trabajador));
    }

    // 🔹 POST: Crear nuevo trabajador
    @PostMapping("/trabajadores")
    public ResponseEntity<TrabajadorDto> create(@RequestBody TrabajadorDto dto) {
        // Verificar si ya existe un trabajador con el mismo NSS
        if (trabajadorService.existsByNss(dto.getNssTrabajador())) {
            return ResponseEntity.badRequest().build();
        }

        Trabajador trabajador = convertToEntity(dto);
        Trabajador saved = trabajadorService.save(trabajador);

        return ResponseEntity.ok(convertToDto(saved));
    }

    // 🔹 PUT: Actualizar trabajador
    @PutMapping("/trabajadores/{id}")
    public ResponseEntity<TrabajadorDto> update(@PathVariable Integer id, @RequestBody TrabajadorDto dto) {
        Trabajador trabajador = convertToEntity(dto);
        Trabajador actualizado = trabajadorService.update(id, trabajador);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(convertToDto(actualizado));
    }

    // 🔹 DELETE: Eliminar trabajador
    @DeleteMapping("/trabajadores/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        trabajadorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Métodos auxiliares para conversión
    private TrabajadorDto convertToDto(Trabajador trabajador) {
        return TrabajadorDto.builder()
                .idTrabajador(trabajador.getIdTrabajador())
                .nssTrabajador(trabajador.getNssTrabajador())
                .nombreTrabajador(trabajador.getNombreTrabajador())
                .especialidadTrabajador(trabajador.getEspecialidadTrabajador())
                .categoriaTrabajador(trabajador.getCategoriaTrabajador())
                .build();
    }

    private Trabajador convertToEntity(TrabajadorDto dto) {
        return Trabajador.builder()
                .nssTrabajador(dto.getNssTrabajador())
                .nombreTrabajador(dto.getNombreTrabajador())
                .especialidadTrabajador(dto.getEspecialidadTrabajador())
                .categoriaTrabajador(dto.getCategoriaTrabajador())
                .build();
    }
}