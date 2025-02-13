package co.com.b2chat.comercioe.handler;

import co.com.b2chat.comercioe.dto.RespuestaGenerica;
import co.com.b2chat.comercioe.excepciones.RecursoNoEncontradoException;
import co.com.b2chat.comercioe.excepciones.StockInsuficienteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<RespuestaGenerica> handleResourceNotFoundException(RecursoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(RespuestaGenerica.builder().mensaje(ex.getMessage()).exito(false).build());
    }

    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<RespuestaGenerica> handleInsufficientStockException(StockInsuficienteException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(RespuestaGenerica.builder().mensaje(ex.getMessage()).exito(false).build());
    }
}
