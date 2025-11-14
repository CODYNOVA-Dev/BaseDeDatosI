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

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String correoCapHum;
        private String contraseñaCapHum;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponse {
        private boolean success;
        private String message;
        private CapitalHumanoDto capitalHumano;
    }
}