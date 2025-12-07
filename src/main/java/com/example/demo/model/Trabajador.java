package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Trabajador")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trabajador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trabajador")
    private Integer idTrabajador;

    @Column(name = "nss_trabajador")
    private String nssTrabajador;

    @Column(name = "nombre_trabajador")
    private String nombreTrabajador;

    @Column(name = "especialidad_trabajador")
    private String especialidadTrabajador;

    @Column(name = "estado_trabajador")
    private String estadoTrabajador;

    @Column(name = "descripcion_trabajador")
    private String descripcionTrabajador;

    @ManyToOne
    @JoinColumn(name = "id_ficha")
    private Ficha ficha;
}