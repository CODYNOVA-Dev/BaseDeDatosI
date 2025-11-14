package com.example.demo.controller;

import com.example.demo.dto.AdminDto;
import com.example.demo.model.Admin;
import com.example.demo.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/indecsa/api")
@RestController
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;


    @PostMapping("/admin/login")
    public ResponseEntity<AdminDto.LoginResponse> login(@RequestBody AdminDto.LoginRequest loginRequest) {
        System.out.println("🔐 Intento de login admin: " + loginRequest.getCorreoAdmin());

        Admin admin = adminService.login(loginRequest.getCorreoAdmin(), loginRequest.getContraseñaAdmin());

        if (admin != null) {

            AdminDto adminDto = AdminDto.builder()
                    .idAdmin(admin.getIdAdmin())
                    .correoAdmin(admin.getCorreoAdmin())
                    .build();

            AdminDto.LoginResponse response = AdminDto.LoginResponse.builder()
                    .success(true)
                    .message("Login exitoso")
                    .admin(adminDto)
                    .build();

            return ResponseEntity.ok(response);
        } else {
            // Login fallido
            AdminDto.LoginResponse response = AdminDto.LoginResponse.builder()
                    .success(false)
                    .message("Credenciales incorrectas")
                    .admin(null)
                    .build();

            return ResponseEntity.status(401).body(response);
        }
    }

    @GetMapping("/admin")
    public ResponseEntity<List<AdminDto>> getAll() {
        List<Admin> lista = adminService.getAll();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                lista.stream()
                        .map(a -> AdminDto.builder()
                                .idAdmin(a.getIdAdmin())
                                .correoAdmin(a.getCorreoAdmin())
                                // No enviar contraseña por seguridad
                                .build())
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("/admin")
    public ResponseEntity<AdminDto> create(@RequestBody AdminDto dto) {
        // Verificar si el correo ya existe
        if (adminService.existsByCorreo(dto.getCorreoAdmin())) {
            return ResponseEntity.badRequest().build();
        }

        Admin admin = Admin.builder()
                .correoAdmin(dto.getCorreoAdmin())
                .contraseñaAdmin(dto.getContraseñaAdmin())
                .build();

        Admin saved = adminService.save(admin);

        return ResponseEntity.ok(AdminDto.builder()
                .idAdmin(saved.getIdAdmin())
                .correoAdmin(saved.getCorreoAdmin())
                .build());
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        adminService.delete(id);
        return ResponseEntity.noContent().build();
    }
}