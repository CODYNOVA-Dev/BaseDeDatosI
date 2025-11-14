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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String correoAdmin;
        private String contraseñaAdmin;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponse {
        private boolean success;
        private String message;
        private AdminDto admin;
        private String token; // Para futura autenticación JWT
    }
}
