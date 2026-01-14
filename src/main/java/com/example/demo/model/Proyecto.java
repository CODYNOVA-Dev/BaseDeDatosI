package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "nombre_proyecto", nullable = false)
    private String nombreProyecto;

    @Column(name = "tipo_proyecto")
    private String tipoProyecto;

    @Column(name = "lugar_proyecto")
    private String lugarProyecto;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    private List<Ficha> fichas = new ArrayList<>();
}
