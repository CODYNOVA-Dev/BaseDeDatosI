package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contratista")
@Data // ← Esto genera automáticamente los getters: getDescripcionContratista()
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contratista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contratista")
    private Integer idContratista;

    @Column(name = "nombre_contratista", length = 100, nullable = false)
    private String nombreContratista;

    @Column(name = "estado_contratista", length = 50)
    private String estadoContratista;

    @Column(name = "description_contratista", length = 100) // ← "description" en BD
    private String descripcionContratista; // ← "descripcion" en Java (con "c")

    @Column(name = "calificacion")
    private Integer calificacion;

    @Column(name = "especialidad", length = 25)
    private String especialidad;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "correo", length = 50)
    private String correo;

    @OneToMany(mappedBy = "contratista", cascade = CascadeType.ALL)
    private List<Ficha> fichas = new ArrayList<>();
}