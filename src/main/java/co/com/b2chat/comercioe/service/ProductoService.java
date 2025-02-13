package co.com.b2chat.comercioe.service;

import co.com.b2chat.comercioe.dto.ProductoDto;
import co.com.b2chat.comercioe.entity.Producto;
import co.com.b2chat.comercioe.excepciones.RecursoNoEncontradoException;
import co.com.b2chat.comercioe.mappers.ProductoMapper;
import co.com.b2chat.comercioe.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoMapper productoMapper = ProductoMapper.INSTANCIA;

    private ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @CacheEvict(value = "productos", allEntries = true)
    public Producto guardarProducto(ProductoDto productoDto) {
        Producto producto = productoMapper.productoDtoToProductoEntity(productoDto);
        return productoRepository.save(producto);
    }

    @Cacheable(value = "productos")
    public List<ProductoDto> obtenerProductos() {
        List<ProductoDto> productos = productoMapper.productoListEntityToListDto(productoRepository.findAll());
        return productos;
    }

    @CachePut(value = "productos", key = "#id")
    public ProductoDto editarProducto(Long id, ProductoDto detalleProducto) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));
        producto.setNombre(detalleProducto.getNombre());
        producto.setDescripcion(detalleProducto.getDescripcion());
        producto.setPrecio(detalleProducto.getPrecio());
        producto.setStock(detalleProducto.getStock());
        return productoMapper.productoEntityToproductoDto(productoRepository.save(producto));
    }

    @CacheEvict(value = "productos", key = "#id")
    public void eliminarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));
        productoRepository.delete(producto);
    }
}
