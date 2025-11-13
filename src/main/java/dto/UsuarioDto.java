package dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UsuarioDto {

    private int id;
    private String user;
    private String nombre;
    private String password;


}
