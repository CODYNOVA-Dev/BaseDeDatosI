package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trabajador")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trabajador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trabajador")
    private Integer idTrabajador;

    private String nssTrabajador;
    private String nombreTrabajador;
    private String especialidadTrabajador;
    private String estadoTrabajador;

    @ManyToOne
    @JoinColumn(name = "id_ficha")
    private Ficha ficha;
}
