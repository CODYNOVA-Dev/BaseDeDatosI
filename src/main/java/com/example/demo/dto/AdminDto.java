package com.example.demo.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {

    private Integer idAdmin;
    private String correoAdmin;
    private String contraseñaAdmin;  // Se mantiene para crear/editar admins


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
        private AdminDto admin; // Se enviará sin contraseña (lo controlas en el controller)
        private String token;
    }
}
