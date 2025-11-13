package controler;

import dto.CapitalHumanoDto;
import model.CapitalHumano;
import service.CapitalHumanoService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Gerdoc/api")
@RestController
@AllArgsConstructor
public class UsuarioCHController {

    private final CapitalHumanoService capitalHumanoService;

    // 🔹 GET: obtener todos o filtrar por correo
    @GetMapping("/capitalhumano")
    public ResponseEntity<List<CapitalHumanoDto>> lista(
            @RequestParam(name = "correo", defaultValue = "", required = false) String correo) {

        List<CapitalHumano> lista = capitalHumanoService.getAll();

        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Filtrar por correo si se envía como parámetro
        if (correo != null && !correo.isEmpty()) {
            lista = lista.stream()
                    .filter(u -> u.getCorreoCapHum().equalsIgnoreCase(correo))
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(
                lista.stream()
                        .map(u -> CapitalHumanoDto.builder()
                                .idCapHum(u.getIdCapHum())
                                .correoCapHum(u.getCorreoCapHum())
                                .contraseñaCapHum(u.getContraseñaCapHum())
                                .build())
                        .collect(Collectors.toList()));
    }

    // 🔹 GET por ID
    @GetMapping("/capitalhumano/{id}")
    public ResponseEntity<CapitalHumanoDto> getById(@PathVariable Integer id) {
        CapitalHumano u = capitalHumanoService.getById(id);
        if (u == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(CapitalHumanoDto.builder()
                .idCapHum(u.getIdCapHum())
                .correoCapHum(u.getCorreoCapHum())
                .contraseñaCapHum(u.getContraseñaCapHum())
                .build());
    }

    // 🔹 POST: insertar nuevo registro
    @PostMapping("/capitalhumano")
    public ResponseEntity<CapitalHumanoDto> save(@RequestBody CapitalHumanoDto dto) {
        CapitalHumano u = CapitalHumano.builder()
                .correoCapHum(dto.getCorreoCapHum())
                .contraseñaCapHum(dto.getContraseñaCapHum())
                .build();

        capitalHumanoService.save(u);

        return ResponseEntity.ok(CapitalHumanoDto.builder()
                .idCapHum(u.getIdCapHum())
                .correoCapHum(u.getCorreoCapHum())
                .contraseñaCapHum(u.getContraseñaCapHum())
                .build());
    }

    // 🔹 DELETE: eliminar por id
    @DeleteMapping("/capitalhumano/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        capitalHumanoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 PUT: actualizar
    @PutMapping("/capitalhumano/{id}")
    public ResponseEntity<CapitalHumanoDto> update(@PathVariable Integer id, @RequestBody CapitalHumanoDto dto) {
        CapitalHumano u = CapitalHumano.builder()
                .correoCapHum(dto.getCorreoCapHum())
                .contraseñaCapHum(dto.getContraseñaCapHum())
                .build();

        CapitalHumano actualizado = capitalHumanoService.update(id, u);

        return ResponseEntity.ok(CapitalHumanoDto.builder()
                .idCapHum(actualizado.getIdCapHum())
                .correoCapHum(actualizado.getCorreoCapHum())
                .contraseñaCapHum(actualizado.getContraseñaCapHum())
                .build());
    }
}

