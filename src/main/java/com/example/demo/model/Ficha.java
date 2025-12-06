package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contratista", nullable = false)
    private Contratista contratista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    @Column(name = "ficha_estado")
    private String fichaEstado;

    @Column(name = "ficha_especialidad")
    private String fichaEspecialidad;

    @OneToMany(mappedBy = "ficha", cascade = CascadeType.ALL)
    private List<Trabajador> trabajadores;
}
