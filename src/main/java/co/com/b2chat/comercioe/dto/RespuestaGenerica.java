package co.com.b2chat.comercioe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RespuestaGenerica<T> {
    private String mensaje;
    private boolean exito;
    private T datos;

}
