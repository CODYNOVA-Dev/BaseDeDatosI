package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
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

    @Column(name = "nombre_proyecto", length = 100, nullable = false)
    private String nombreProyecto;

    @Column(name = "tipo_proyecto", length = 50)
    private String tipoProyecto;

    @Column(name = "lugar_proyecto", length = 100)
    private String lugarProyecto;

    @Column(name = "fechainicio_proyecto")
    private LocalDate fechaInicioProyecto;

    @Column(name = "fechafin_proyecto")
    private LocalDate fechaFinProyecto;

    @Column(name = "cliente_proyecto", length = 50)
    private String clienteProyecto;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    private List<Ficha> fichas = new ArrayList<>();
}