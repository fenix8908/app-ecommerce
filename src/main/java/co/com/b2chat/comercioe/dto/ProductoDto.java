package co.com.b2chat.comercioe.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -1622650956092814927L;

    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
}
