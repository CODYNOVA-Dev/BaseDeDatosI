package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FichaCompletaDto {
    private Integer idFicha;
    private String fichaEstado;
    private String fichaEspecialidad;

    // Datos del contratista
    private String nombreContratista;
    private String descripcionContratista;
    private String telefonoContratista;
    private String correoContratista;
    private String especialidadContratista;

    // Datos del proyecto
    private String nombreProyecto;
    private String lugarProyecto;
    private String tipoProyecto;

    // Equipo de trabajo
    private String equipoTrabajo;
}