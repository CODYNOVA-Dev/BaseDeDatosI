package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "proyecto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto")
    private Integer idProyecto;

    @Column(name = "nombre_proyecto", nullable = false, length = 100)
    private String nombreProyecto;

    @Column(name = "tipo_proyecto", length = 50)
    private String tipoProyecto;

    @Column(name = "lugar_proyecto", length = 100)
    private String lugarProyecto;

    @Column(name = "cliente_proyecto", length = 50)
    private String clienteProyecto;

    @Column(name = "presupuesto", precision = 15, scale = 2)
    private BigDecimal presupuesto;
}
