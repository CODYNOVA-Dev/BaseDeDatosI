package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "capitalhumano")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CapitalHumano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_CapHum")
    private Integer idCapHum;

    @Column(name = "correo_CapHum", length = 50, unique = true)
    private String correoCapHum;

    @Column(name = "contraseña_CapHum", length = 255)
    private String contraseñaCapHum;

    // 🔄 RELACIÓN CON FICHA
    @OneToMany(mappedBy = "capitalHumano", cascade = CascadeType.ALL)
    private List<Ficha> fichas = new ArrayList<>();
}