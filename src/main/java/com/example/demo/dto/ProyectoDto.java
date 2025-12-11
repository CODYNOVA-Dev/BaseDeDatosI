package com.example.demo.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoDto {
    private Integer idProyecto;
    private String nombreProyecto;
    private String tipoProyecto;
    private String lugarProyecto;
    // ELIMINA estos campos que ya no existen en la BD:
    // private LocalDate fechaInicioProyecto;
    // private LocalDate fechaFinProyecto;
    // private String clienteProyecto;
}