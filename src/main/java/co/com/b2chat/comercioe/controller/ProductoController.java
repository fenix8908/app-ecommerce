package co.com.b2chat.comercioe.controller;

import co.com.b2chat.comercioe.dto.ProductoDto;
import co.com.b2chat.comercioe.entity.Producto;
import co.com.b2chat.comercioe.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/crear")
    public ResponseEntity<Producto> addProduct(@RequestBody ProductoDto productoDto) {
        Producto productoGuardado = productoService.guardarProducto(productoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoGuardado);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductoDto>> getAllProducts() {
        List<ProductoDto> products = productoService.obtenerProductos();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ProductoDto> updateProduct(@PathVariable Long id, @RequestBody ProductoDto productoDto) {
        ProductoDto productoEditado = productoService.editarProducto(id, productoDto);
        return ResponseEntity.ok(productoEditado);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
