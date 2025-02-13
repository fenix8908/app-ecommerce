package co.com.b2chat.comercioe.dto;

import co.com.b2chat.comercioe.entity.DetallePedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDto {
    List<DetallePedido> items;
}
