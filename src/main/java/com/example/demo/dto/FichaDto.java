package com.example.demo.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FichaDto {
    private Integer idFicha;
    private Integer idContratista;
    private Integer idProyecto;
    private Integer idAdmin;
    private Integer idCapHum;

    // Opcional: incluir datos completos de las relaciones
    private ContratistaDto contratista;
    private ProyectoDto proyecto;
    private AdminDto admin;
    private CapitalHumanoDto capitalHumano;
}