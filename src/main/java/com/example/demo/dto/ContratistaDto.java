package com.example.demo.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContratistaDto {
    private Integer idContratista;
    private String nombreContratista;
    private String estadoContratista;
    private String descripcionContratista; // ← CON "C": descripcion
    private Integer calificacion;
    private String especialidad;
    private String telefono;
    private String correo;
}