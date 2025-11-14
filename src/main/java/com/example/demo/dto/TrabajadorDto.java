package com.example.demo.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrabajadorDto {
    private Integer idTrabajador;
    private String nssTrabajador;
    private String nombreTrabajador;
    private String especialidadTrabajador;
    private String categoriaTrabajador;
}