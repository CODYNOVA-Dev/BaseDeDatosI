package com.example.demo.controler;

import com.example.demo.dto.CapitalHumanoDto;
import com.example.demo.model.CapitalHumano;
import com.example.demo.service.CapitalHumanoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/indecsa/api")
@RestController
@AllArgsConstructor
public class CapitalHumanoController {

    private final CapitalHumanoService capitalHumanoService;

    @PostMapping("/capitalhumano/login")
    public ResponseEntity<CapitalHumanoDto.LoginResponse> login(@RequestBody CapitalHumanoDto.LoginRequest loginRequest) {
        System.out.println("🔐 Intento de login: " + loginRequest.getCorreoCapHum());

        CapitalHumano capitalHumano = capitalHumanoService.login(
                loginRequest.getCorreoCapHum(),
                loginRequest.getContraseñaCapHum()
        );

        if (capitalHumano != null) {
            CapitalHumanoDto capitalHumanoDto = CapitalHumanoDto.builder()
                    .idCapHum(capitalHumano.getIdCapHum())
                    .correoCapHum(capitalHumano.getCorreoCapHum())
                    .build();

            CapitalHumanoDto.LoginResponse response = CapitalHumanoDto.LoginResponse.builder()
                    .success(true)
                    .message("Login exitoso")
                    .capitalHumano(capitalHumanoDto)
                    .build();

            return ResponseEntity.ok(response);
        } else {
            CapitalHumanoDto.LoginResponse response = CapitalHumanoDto.LoginResponse.builder()
                    .success(false)
                    .message("Credenciales incorrectas")
                    .capitalHumano(null)
                    .build();

            return ResponseEntity.status(401).body(response);
        }
    }

    @GetMapping("/capitalhumano")
    public ResponseEntity<List<CapitalHumanoDto>> lista(
            @RequestParam(name = "correo", defaultValue = "", required = false) String correo) {

        List<CapitalHumano> lista = capitalHumanoService.getAll();

        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

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

    @GetMapping("/capitalhumano/{id}")
    public ResponseEntity<CapitalHumanoDto> getById(@PathVariable("id") Integer id) {
        CapitalHumano u = capitalHumanoService.getById(id);

        if (u == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                CapitalHumanoDto.builder()
                        .idCapHum(u.getIdCapHum())
                        .correoCapHum(u.getCorreoCapHum())
                        .contraseñaCapHum(u.getContraseñaCapHum())
                        .build()
        );
    }

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

    @DeleteMapping("/capitalhumano/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        capitalHumanoService.delete(id);
        return ResponseEntity.noContent().build();
    }

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