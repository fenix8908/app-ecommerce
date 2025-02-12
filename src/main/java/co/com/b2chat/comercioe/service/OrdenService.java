package co.com.b2chat.comercioe.service;

import co.com.b2chat.comercioe.OrdenEstado;
import co.com.b2chat.comercioe.entity.Orden;
import co.com.b2chat.comercioe.entity.DetallePedido;
import co.com.b2chat.comercioe.entity.Producto;
import co.com.b2chat.comercioe.entity.Usuario;
import co.com.b2chat.comercioe.excepciones.RecursoNoEncontradoException;
import co.com.b2chat.comercioe.excepciones.StockInsuficienteException;
import co.com.b2chat.comercioe.repository.OrdenRepository;
import co.com.b2chat.comercioe.repository.ProductoRepository;
import co.com.b2chat.comercioe.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenService {


    private final OrdenRepository ordenRepository;

    private final UsuarioRepository usuarioRepository;

    private final ProductoRepository productoRepository;

    @Autowired
    public OrdenService(OrdenRepository ordenRepository, UsuarioRepository usuarioRepository,
                        ProductoRepository productoRepository) {
        this.ordenRepository = ordenRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
    }

    public Orden crearOrden(Long userId, List<DetallePedido> items) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        Orden orden = new Orden();
        orden.setUsuario(usuario);
        orden.setEstado(OrdenEstado.PENDIENTE);

        for (DetallePedido item : items) {
            Producto producto = productoRepository.findById(item.getProducto().getId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));

            if (producto.getStock() < item.getCantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            item.setOrden(orden);
        }

        orden.setItems(items);
        return ordenRepository.save(orden);
    }

    public Orden obtenerOrdenPorId(Long id) {
        return ordenRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado"));
    }

    public Orden actualizarEstadoOrden(Long id, OrdenEstado estado) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado"));
        orden.setEstado(estado);
        return ordenRepository.save(orden);
    }
}
