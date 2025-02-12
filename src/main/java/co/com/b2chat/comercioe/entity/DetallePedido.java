package co.com.b2chat.comercioe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orden orden;

    private Integer cantidad;
}
