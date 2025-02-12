package co.com.b2chat.comercioe.service;

import co.com.b2chat.comercioe.entity.Producto;
import co.com.b2chat.comercioe.excepciones.RecursoNoEncontradoException;
import co.com.b2chat.comercioe.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private ProductoRepository productoRepository;

    public Producto guaradarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }

    public Producto editarProducto(Long id, Producto detalleProducto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));
        producto.setNombre(detalleProducto.getNombre());
        producto.setDescripcion(detalleProducto.getDescripcion());
        producto.setPrecio(detalleProducto.getPrecio());
        producto.setStock(detalleProducto.getStock());
        return productoRepository.save(producto);
    }

    public void eliminarProduct(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));
        productoRepository.delete(producto);
    }
}
