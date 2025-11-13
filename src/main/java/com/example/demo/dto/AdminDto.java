package com.example.demo.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    private Integer idAdmin;
    private String correoAdmin;
    private String contraseñaAdmin;

    // DTO para login (sin id)
    @Data
    @Builder
    public static class LoginRequest {
        private String correoAdmin;
        private String contraseñaAdmin;
    }

    // DTO para respuesta de login
    @Data
    @Builder
    public static class LoginResponse {
        private boolean success;
        private String message;
        private AdminDto admin;
        private String token; // Para futura autenticación JWT
    }
}