package com.example.demo.controler;

import com.example.demo.dto.FichaDto;
import com.example.demo.model.*;
import com.example.demo.service.FichaService;
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

    // 🔹 GET: Obtener todas las fichas
    @GetMapping("/fichas")
    public ResponseEntity<List<FichaDto>> getAll() {
        List<Ficha> lista = fichaService.getAll();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                lista.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList())
        );
    }

    // 🔹 GET: Obtener ficha por ID
    @GetMapping("/fichas/{id}")
    public ResponseEntity<FichaDto> getById(@PathVariable Integer id) {
        Ficha ficha = fichaService.getById(id);
        if (ficha == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDto(ficha));
    }

    // 🔹 GET: Obtener fichas por Proyecto
    @GetMapping("/fichas/proyecto/{idProyecto}")
    public ResponseEntity<List<FichaDto>> getByProyecto(@PathVariable Integer idProyecto) {
        List<Ficha> fichas = fichaService.getByProyecto(idProyecto);
        return ResponseEntity.ok(
                fichas.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList())
        );
    }

    // 🔹 POST: Crear nueva ficha
    @PostMapping("/fichas")
    public ResponseEntity<FichaDto> create(@RequestBody FichaDto dto) {
        try {
            Ficha ficha = convertToEntity(dto);
            Ficha saved = fichaService.save(ficha);
            return ResponseEntity.ok(convertToDto(saved));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 🔹 PUT: Actualizar ficha
    @PutMapping("/fichas/{id}")
    public ResponseEntity<FichaDto> update(@PathVariable Integer id, @RequestBody FichaDto dto) {
        Ficha ficha = convertToEntity(dto);
        Ficha actualizado = fichaService.update(id, ficha);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(convertToDto(actualizado));
    }

    // 🔹 DELETE: Eliminar ficha
    @DeleteMapping("/fichas/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        fichaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Métodos auxiliares para conversión
    private FichaDto convertToDto(Ficha ficha) {
        return FichaDto.builder()
                .idFicha(ficha.getIdFicha())
                .idContratista(ficha.getContratista() != null ? ficha.getContratista().getIdContratista() : null)
                .idProyecto(ficha.getProyecto() != null ? ficha.getProyecto().getIdProyecto() : null)
                .idAdmin(ficha.getAdmin() != null ? ficha.getAdmin().getIdAdmin() : null)
                .idCapHum(ficha.getCapitalHumano() != null ? ficha.getCapitalHumano().getIdCapHum() : null)
                .build();
    }

    private Ficha convertToEntity(FichaDto dto) {
        Ficha ficha = new Ficha();

        // Establecer relaciones si se proporcionan IDs
        if (dto.getIdContratista() != null) {
            Contratista contratista = new Contratista();
            contratista.setIdContratista(dto.getIdContratista());
            ficha.setContratista(contratista);
        }

        if (dto.getIdProyecto() != null) {
            Proyecto proyecto = new Proyecto();
            proyecto.setIdProyecto(dto.getIdProyecto());
            ficha.setProyecto(proyecto);
        }

        if (dto.getIdAdmin() != null) {
            Admin admin = new Admin();
            admin.setIdAdmin(dto.getIdAdmin());
            ficha.setAdmin(admin);
        }

        if (dto.getIdCapHum() != null) {
            CapitalHumano capitalHumano = new CapitalHumano();
            capitalHumano.setIdCapHum(dto.getIdCapHum());
            ficha.setCapitalHumano(capitalHumano);
        }

        return ficha;
    }
}