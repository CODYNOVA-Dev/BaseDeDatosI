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

    @Column(name = "nss_trabajador", length = 15, unique = true)
    private String nssTrabajador;

    @Column(name = "nombre_trabajador", length = 100, nullable = false)
    private String nombreTrabajador;

    @Column(name = "especialidad_trabajador", length = 50)
    private String especialidadTrabajador;

    @Column(name = "categoria_trabajador", length = 50)
    private String categoriaTrabajador;
}