package com.example.demo.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProyectoDto {

    private Integer idProyecto;
    private String nombreProyecto;
    private String tipoProyecto;
    private String lugarProyecto;
    private String clienteProyecto;
    private BigDecimal presupuesto;
}
