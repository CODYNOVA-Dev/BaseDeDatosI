package com.example.demo.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CapitalHumanoDto {
    private Integer idCapHum;
    private String correoCapHum;
    private String contraseñaCapHum;

    // 🔐 DTO para login
    @Data
    @Builder
    public static class LoginRequest {
        private String correoCapHum;
        private String contraseñaCapHum;
    }

    // 🔐 DTO para respuesta de login
    @Data
    @Builder
    public static class LoginResponse {
        private boolean success;
        private String message;
        private CapitalHumanoDto capitalHumano;
    }
}