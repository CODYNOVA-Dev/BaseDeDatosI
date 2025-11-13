package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_admin")
    private Integer idAdmin;

    @Column(name = "correo_admin", length = 50, unique = true)
    private String correoAdmin;

    @Column(name = "contraseña_admin", length = 255)
    private String contraseñaAdmin;
}