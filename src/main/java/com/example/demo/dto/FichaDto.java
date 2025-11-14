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

    // ❌ QUITAR ESTOS - solo usar IDs
    // private ContratistaDto contratista;
    // private ProyectoDto proyecto;
    // private AdminDto admin;
    // private CapitalHumanoDto capitalHumano;
}