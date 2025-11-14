package com.example.demo.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoDto {
    private Integer idProyecto;
    private String nombreProyecto;
    private String tipoProyecto;
    private String lugarProyecto;
    private LocalDate fechaInicioProyecto;
    private LocalDate fechaFinProyecto;
    private String clienteProyecto;
}