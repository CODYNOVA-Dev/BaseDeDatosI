package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CapitalHumanoDto {
    private Integer idCapHum;
    private String correoCapHum;
    private String contraseñaCapHum;
}
