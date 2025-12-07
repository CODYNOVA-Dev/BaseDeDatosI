package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrabajadorDto {
    private Integer idTrabajador;
    private String nssTrabajador;
    private String nombreTrabajador;
    private String especialidadTrabajador;
    private String estadoTrabajador;
    private String descripcionTrabajador;
}