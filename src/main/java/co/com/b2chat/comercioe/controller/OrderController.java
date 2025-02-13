package co.com.b2chat.comercioe.controller;

import co.com.b2chat.comercioe.OrdenEstado;
import co.com.b2chat.comercioe.dto.PedidoDto;
import co.com.b2chat.comercioe.dto.RespuestaGenerica;
import co.com.b2chat.comercioe.entity.Orden;
import co.com.b2chat.comercioe.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrdenService ordenService;

    @PostMapping("/{userId}")
    public ResponseEntity<RespuestaGenerica> crearPedido(@PathVariable Long userId, @RequestBody PedidoDto pedidoDto) {
        RespuestaGenerica respuesta = ordenService.crearOrden(userId, pedidoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orden> obtenerPedidoPorId(@PathVariable Long id) {
        Orden orden = ordenService.obtenerOrdenPorId(id);
        return ResponseEntity.ok(orden);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Orden> actualizarEstadoOrder(@PathVariable Long id, @RequestParam OrdenEstado estado) {
        Orden orden = ordenService.actualizarEstadoOrden(id, estado);
        return ResponseEntity.ok(orden);
    }
}