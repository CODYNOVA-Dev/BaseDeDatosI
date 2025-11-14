package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CapitalHumano")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CapitalHumano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_CapHum")
    private Integer idCapHum;

    @Column(name = "correo_CapHum", nullable = false, length = 50)
    private String correoCapHum;

    @Column(name = "contraseña_CapHum", nullable = false, length = 255)
    private String contraseñaCapHum;
}