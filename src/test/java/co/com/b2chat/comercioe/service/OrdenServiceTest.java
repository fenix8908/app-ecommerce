package co.com.b2chat.comercioe.service;

import co.com.b2chat.comercioe.OrdenEstado;
import co.com.b2chat.comercioe.dto.PedidoDto;
import co.com.b2chat.comercioe.dto.RespuestaGenerica;
import co.com.b2chat.comercioe.entity.DetallePedido;
import co.com.b2chat.comercioe.entity.Orden;
import co.com.b2chat.comercioe.entity.Producto;
import co.com.b2chat.comercioe.entity.Usuario;
import co.com.b2chat.comercioe.excepciones.RecursoNoEncontradoException;
import co.com.b2chat.comercioe.excepciones.StockInsuficienteException;
import co.com.b2chat.comercioe.repository.OrdenRepository;
import co.com.b2chat.comercioe.repository.ProductoRepository;
import co.com.b2chat.comercioe.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OrdenServiceTest {


    @Mock
    private OrdenRepository ordenRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private OrdenService ordenService;


    @Test
    void testCrearOrden_Exitoso() {
        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);

        PedidoDto pedidoDto = new PedidoDto();
        DetallePedido detallePedido = new DetallePedido();
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setStock(10);
        producto.setNombre("Producto 1");
        detallePedido.setProducto(producto);
        detallePedido.setCantidad(2);
        pedidoDto.setItems(Collections.singletonList(detallePedido));

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(ordenRepository.save(any(Orden.class))).thenReturn(new Orden());

        RespuestaGenerica respuesta = ordenService.crearOrden(userId, pedidoDto);

        assertTrue(respuesta.isExito());
        assertEquals("El pedido ha sido creado", respuesta.getMensaje());
    }

    @Test
    void testCrearOrden_NoExitoso() {
        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);

        PedidoDto pedidoDto = new PedidoDto();
        DetallePedido detallePedido = new DetallePedido();
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setStock(10);
        producto.setNombre("Producto 1");
        detallePedido.setProducto(producto);
        detallePedido.setCantidad(2);
        pedidoDto.setItems(Collections.singletonList(detallePedido));

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(ordenRepository.save(any(Orden.class))).thenReturn(null);

        RespuestaGenerica respuesta = ordenService.crearOrden(userId, pedidoDto);

        assertFalse(respuesta.isExito());
        assertEquals("No fue posible crear el pedido", respuesta.getMensaje());
    }

    @Test
    void testCrearOrden_UsuarioNoEncontrado() {
        Long userId = 1L;
        PedidoDto pedidoDto = new PedidoDto();
        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> {
            ordenService.crearOrden(userId, pedidoDto);
        });
    }

    @Test
    void testCrearOrden_ProductoNoEncontrado() {
        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        PedidoDto pedidoDto = new PedidoDto();
        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setProducto(new Producto());
        detallePedido.getProducto().setId(1L);
        pedidoDto.setItems(Collections.singletonList(detallePedido));

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> {
            ordenService.crearOrden(userId, pedidoDto);
        });
    }

    @Test
    void testCrearOrden_StockInsuficiente() {
        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        PedidoDto pedidoDto = new PedidoDto();
        DetallePedido detallePedido = new DetallePedido();
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setStock(1);
        detallePedido.setProducto(producto);
        detallePedido.setCantidad(5);
        pedidoDto.setItems(Collections.singletonList(detallePedido));

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        assertThrows(StockInsuficienteException.class, () -> {
            ordenService.crearOrden(userId, pedidoDto);
        });
    }

    @Test
    void testObtenerOrdenPorId_Exitoso() {
        Long ordenId = 1L;
        Orden orden = new Orden();
        orden.setId(ordenId);
        when(ordenRepository.findById(ordenId)).thenReturn(Optional.of(orden));

        Orden resultado = ordenService.obtenerOrdenPorId(ordenId);

        assertNotNull(resultado);
        assertEquals(ordenId, resultado.getId());
    }

    @Test
    void testObtenerOrdenPorId_NoEncontrado() {
        Long ordenId = 1L;
        when(ordenRepository.findById(ordenId)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> {
            ordenService.obtenerOrdenPorId(ordenId);
        });
    }

    @Test
    void testActualizarEstadoOrden_Exitoso() {
        Long ordenId = 1L;
        Orden orden = new Orden();
        orden.setId(ordenId);
        orden.setEstado(OrdenEstado.PENDIENTE);

        when(ordenRepository.findById(ordenId)).thenReturn(Optional.of(orden));
        when(ordenRepository.save(any(Orden.class))).thenReturn(orden);

        Orden resultado = ordenService.actualizarEstadoOrden(ordenId, OrdenEstado.EN_PROCESO);

        assertNotNull(resultado);
        assertEquals(OrdenEstado.EN_PROCESO, resultado.getEstado());
    }

    @Test
    void testActualizarEstadoOrden_NoEncontrado() {
        Long ordenId = 1L;
        when(ordenRepository.findById(ordenId)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> {
            ordenService.actualizarEstadoOrden(ordenId, OrdenEstado.EN_PROCESO);
        });
    }

}