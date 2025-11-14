package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ficha")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ficha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ficha")
    private Integer idFicha;

    // 🔄 RELACIÓN CON CONTRATISTA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contratista")
    private Contratista contratista;

    // 🔄 RELACIÓN CON PROYECTO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto")
    private Proyecto proyecto;

    // 🔄 RELACIÓN CON ADMIN
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_admin")
    private Admin admin;

    // 🔄 RELACIÓN CON CAPITAL HUMANO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_CapHum")
    private CapitalHumano capitalHumano;
}