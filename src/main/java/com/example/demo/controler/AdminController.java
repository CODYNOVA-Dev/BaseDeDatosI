package com.example.demo.controler;

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

    // ============================================================
    //                     LOGIN ADMIN
    // ============================================================
    @PostMapping("/admin/login")
    public ResponseEntity<AdminDto.LoginResponse> login(@RequestBody AdminDto.LoginRequest loginRequest) {

        Admin admin = adminService.login(loginRequest.getCorreoAdmin(),
                loginRequest.getContraseñaAdmin());

        if (admin != null) {

            // 🔥 AQUÍ YA SE ENVÍA LA CONTRASEÑA
            AdminDto adminDto = AdminDto.builder()
                    .idAdmin(admin.getIdAdmin())
                    .correoAdmin(admin.getCorreoAdmin())
                    .contraseñaAdmin(admin.getContraseñaAdmin())
                    .build();

            AdminDto.LoginResponse response = AdminDto.LoginResponse.builder()
                    .success(true)
                    .message("Login exitoso")
                    .admin(adminDto)
                    .token(null)
                    .build();

            return ResponseEntity.ok(response);

        } else {

            AdminDto.LoginResponse response = AdminDto.LoginResponse.builder()
                    .success(false)
                    .message("Credenciales incorrectas")
                    .admin(null)
                    .token(null)
                    .build();

            return ResponseEntity.status(401).body(response);
        }
    }


    // ============================================================
    //               LISTAR ADMIN → ENVÍA CONTRASEÑA
    // ============================================================
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
                                .contraseñaAdmin(a.getContraseñaAdmin())   // 🔥 AQUI TAMBIÉN
                                .build())
                        .collect(Collectors.toList())
        );
    }


    // ============================================================
    //                CREAR ADMIN → ENVÍA CONTRASEÑA
    // ============================================================
    @PostMapping("/admin")
    public ResponseEntity<AdminDto> create(@RequestBody AdminDto dto) {

        if (adminService.existsByCorreo(dto.getCorreoAdmin())) {
            return ResponseEntity.badRequest().build();
        }

        Admin admin = Admin.builder()
                .correoAdmin(dto.getCorreoAdmin())
                .contraseñaAdmin(dto.getContraseñaAdmin())
                .build();

        Admin saved = adminService.save(admin);

        // 🔥 REGRESA CONTRASEÑA TAMBIÉN
        return ResponseEntity.ok(AdminDto.builder()
                .idAdmin(saved.getIdAdmin())
                .correoAdmin(saved.getCorreoAdmin())
                .contraseñaAdmin(saved.getContraseñaAdmin())
                .build());
    }


    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        adminService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
