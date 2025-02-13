package co.com.b2chat.comercioe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductoDto {

    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
}
