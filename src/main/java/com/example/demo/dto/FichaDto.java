package com.example.demo.dto;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FichaDto {

    private Integer idFicha;
    private Integer idContratista;
    private Integer idProyecto;

    private String fichaEstado;
    private String fichaEspecialidad;

    private List<Integer> trabajadoresIds;
}
